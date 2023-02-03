package com.saludaunclic.semefa.common.service

import com.ibm.mq.MQException
import com.ibm.mq.MQGetMessageOptions
import com.ibm.mq.MQMessage
import com.ibm.mq.MQQueue
import com.ibm.mq.constants.CMQC
import com.saludaunclic.semefa.common.config.MqProperties
import com.saludaunclic.semefa.common.throwing.MqMaxAttemptReachedException
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import java.util.Hashtable
import java.util.concurrent.TimeUnit

@Service
class MqClientService(private val mqProperties: MqProperties) {
    companion object {
        const val MESSAGE_ID_KEY = "MsgId"
        const val MESSAGE_KEY = "Msg"
        const val CHARACTER_SET = 819
        const val ENCODING = 273
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val connectionProps = Hashtable<String, Any>()
        .apply {
            with(mqProperties) {
                put(CMQC.CHANNEL_PROPERTY, channel)
                put(CMQC.PORT_PROPERTY, port)
                put(CMQC.HOST_NAME_PROPERTY, hostname)
                put(CMQC.APPNAME_PROPERTY, "Aplicación Afiliación Online JAVA, SEMEFA-SUSALUD V1.0")
                put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT)
                logger.info("""

                    Loaded MQ properties:
                    =====================
                    queueManagerName: $queueManager
                    queueNameIn: $queueIn
                    queueNameOut: $queueOut
                    hostname: $hostname 
                    channel: $channel
                    port: $port
                    numberOfTries: $numberOfTries
                    waitAfterTry: $waitAfterTry
                    waitInterval: $waitInterval

                """.trimIndent())
            }
        }

    fun sendMessageSync(message: String): Map<String, String> {
        logger.info("Connecting to queue manager: ${mqProperties.queueManager} to send update")
        val wrapper = QueueManagerWrapper(mqProperties)

        try {
            wrapper.wrap(connectionProps)
            val putMessage: MQMessage = createPutMessage(message)
            wrapper.queueIn.put(putMessage)
            return getMessageResponse(wrapper.queueOut, createGetMessage(putMessage.messageId))
        } finally {
            wrapper.closeResources()
            logger.info("=== End MQ Connection ===")
        }
    }

    fun fetchMessage(messageId: String): Map<String, String> {
        logger.info("Connecting to queue manager: ${mqProperties.queueManager} to recover message $messageId")
        val wrapper = QueueManagerWrapper(mqProperties)

        try {
            wrapper.wrap(connectionProps)
            return getMessageResponse(wrapper.queueOut, createGetMessage(messageId))
        } finally {
            wrapper.closeResources()
        }
    }

    private fun createMessageOptions(): MQGetMessageOptions = MQGetMessageOptions()
        .apply {
            matchOptions = CMQC.MQMO_MATCH_MSG_ID
            options = CMQC.MQGMO_WAIT
            waitInterval = mqProperties.waitInterval
        }

    private fun getMessageResponse(queueOut: MQQueue, message: MQMessage): MutableMap<String, String> {
        fetchResponse(queueOut, message)
        return processResponse(message)
    }

    private fun fetchResponse(queueOut: MQQueue, message: MQMessage) {
        for(i in 1..mqProperties.numberOfTries) {
            logger.info("Attempt #$i to fetch response message")
            try {
                queueOut.get(message, createMessageOptions())
                return
            } catch (ex: Exception) {
                if (i < mqProperties.numberOfTries) {
                    logger.info("Attempt #$i to fetch response message has failed due to ${ex.message}")
                    TimeUnit.SECONDS.sleep(mqProperties.waitAfterTry)
                } else if (ex is MQException) {
                    throw ex
                }
            }
        }

        val messageId = StringUtils.defaultString(Hex.encode(message.messageId).contentToString())
        throw MqMaxAttemptReachedException(
            messageId,
            "Límite de intentos (${mqProperties.numberOfTries}) para consumir MQ se alcanzó, messageId: [$messageId]")
    }

    private fun processResponse(message: MQMessage): MutableMap<String, String> {
        val response = mutableMapOf<String, String>()

        if (message.dataLength == 0) {
            logger.warn("Get message does not contain any data")
            return response
        }

        logger.info("Ready message fetch")
        val xmlDataFrame: String = message.readStringOfByteLength(message.dataLength)
        logger.debug("Message got: $xmlDataFrame")

        val messageId = String(Hex.encode(message.messageId))
        logger.info("Msg Id: [ $messageId ]")

        response[MESSAGE_ID_KEY] = messageId
        response[MESSAGE_KEY] = xmlDataFrame

        return response
    }

    private fun createPutMessage(message: String): MQMessage =
        MQMessage()
            .apply {
                logger.info("Creating PUT message")
                characterSet = CHARACTER_SET
                encoding = ENCODING
                format = CMQC.MQFMT_STRING
                writeString(message)
            }

    private fun createGetMessage(messageId: ByteArray): MQMessage =
        MQMessage()
            .apply {
                logger.info("Creating GET message")
                characterSet = CHARACTER_SET
                encoding = ENCODING
                this.messageId = messageId
                logger.debug("Message: [ ${this.messageId} ]")
            }

    private fun createGetMessage(messageId: String): MQMessage = createGetMessage(Hex.decode(messageId))
}

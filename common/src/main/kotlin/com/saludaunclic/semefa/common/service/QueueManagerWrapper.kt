package com.saludaunclic.semefa.common.service

import com.ibm.mq.MQMessage
import com.ibm.mq.MQQueue
import com.ibm.mq.MQQueueManager
import com.ibm.mq.constants.CMQC
import com.saludaunclic.semefa.common.config.MqProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Hashtable

class QueueManagerWrapper(private val mqProperties: MqProperties) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    lateinit var queueManager: MQQueueManager
    lateinit var queueIn: MQQueue
    lateinit var queueOut: MQQueue
    lateinit var putMessage: MQMessage
    lateinit var getMessage: MQMessage

    fun wrap(connectionProps: Hashtable<String, Any>): QueueManagerWrapper =
        setAndChain {
            queueManager = MQQueueManager(mqProperties.queueManager, connectionProps)
                .also { logger.info("Connected to: ${mqProperties.queueManager}") }
        }.apply {
            queueIn = accessQueue(mqProperties.queueIn, CMQC.MQOO_OUTPUT)
            queueOut = accessQueue(mqProperties.queueOut, CMQC.MQOO_INPUT_AS_Q_DEF)
        }

    fun closeResources() {
        // Clear messages
        try {
            if (this::putMessage.isInitialized) {
                logger.info("Clear put message")
                putMessage.clearMessage()
            }
        } catch (ex: Exception) {
            logger.error("Error clearing message put")
        }
        try {
            if (this::getMessage.isInitialized) {
                logger.info("Clear get message")
                getMessage.clearMessage()
            }
        } catch (ex: Exception) {
            logger.error("Error clearing get message")
        }
        // Close the queues
        try {
            if (this::queueIn.isInitialized) {
                logger.info("Closing the queueIn")
                queueIn.close()
            }
        } catch (ex: Exception) {
            logger.error("Error closing queueIn")
        }
        try {
            if (this::queueOut.isInitialized) {
                logger.info("Closing the queueOut")
                queueOut.close()
            }
        } catch (ex: Exception) {
            logger.error("Error closing queueOut")
        }
        // Disconnect from the QueueManager
        try {
            if (this::queueManager.isInitialized) {
                logger.info("Disconnecting from the Queue Manager")
                queueManager.disconnect()
            }
        } catch (ex: Exception) {
            logger.error("Error disconnecting Queue Manager")
        }
    }

    private fun accessQueue(queueName: String, partialOptions: Int): MQQueue =
        with(queueName) {
            logger.info("Accessing queue $this.. ")
            queueManager
                .accessQueue(this, partialOptions or CMQC.MQOO_FAIL_IF_QUIESCING)
                .also { logger.info("Queue $this accessed") }
        }

    private fun setAndChain(setter: (QueueManagerWrapper) -> Unit): QueueManagerWrapper =
        with(this) {
            setter(this)
            this
        }
}
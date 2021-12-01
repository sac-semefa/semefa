package com.saludaunclic.semefa.regafi.service

import com.saludaunclic.semefa.regafi.repository.FieldErrorRepository
import com.saludaunclic.semefa.regafi.repository.FieldErrorRuleRepository
import com.saludaunclic.semefa.regafi.repository.ServiceErrorRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ErrorsService(
    private val fieldErrorRepository: FieldErrorRepository,
    private val fieldErrorRuleRepository: FieldErrorRuleRepository,
    private val serviceErrorRepository: ServiceErrorRepository
) {
    companion object {
        val lock = Object()
    }

    private lateinit var fieldErrorMap: Map<Int, String>
    private lateinit var fieldErrorRuleMap: Map<Int, String>
    private lateinit var serviceErrorMap: Map<Int, String>

    fun getFieldError(id: Int): String? = fieldErrorMap[id]

    fun getFieldErrorRule(id: Int): String? = fieldErrorRuleMap[id]

    fun getServiceError(id: Int): String? = serviceErrorMap[id]

    @PostConstruct
    fun reset() {
        synchronized(lock) {
            fieldErrorMap = fieldErrorRepository.findAll().associate { it.id to it.name }
            fieldErrorRuleMap = fieldErrorRuleRepository.findAll().associate { it.id to it.description }
            serviceErrorMap = serviceErrorRepository.findAll().associate { it.id to it.description }
        }
    }
}

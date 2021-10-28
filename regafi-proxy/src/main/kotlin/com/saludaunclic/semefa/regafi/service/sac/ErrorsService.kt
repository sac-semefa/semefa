package com.saludaunclic.semefa.regafi.service.sac

import com.saludaunclic.semefa.regafi.repository.FieldErrorRepository
import com.saludaunclic.semefa.regafi.repository.FieldErrorRuleRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ErrorsService(
    val fieldErrorRepository: FieldErrorRepository,
    val fieldErrorRuleRepository: FieldErrorRuleRepository
) {
    companion object {
        val lock = Object()
    }

    private lateinit var fieldErrorMap: Map<Int, String>
    private lateinit var fieldErrorRuleMap: Map<Int, String>

    fun getFieldError(id: Int): String? {
        return fieldErrorMap[id]
    }

    fun getFieldErrorRule(id: Int): String? {
        return fieldErrorRuleMap[id]
    }

    @PostConstruct
    fun reset() {
        synchronized(lock) {
            fieldErrorMap = fieldErrorRepository
                .findAll()
                .associate { it.id to it.name }
            fieldErrorRuleMap = fieldErrorRuleRepository
                .findAll()
                .associate { it.id to it.description }
        }
    }
}

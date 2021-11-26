package com.saludaunclic.semefa.regafi.service

import com.saludaunclic.semefa.regafi.repository.FieldErrorRepository
import com.saludaunclic.semefa.regafi.repository.FieldErrorRuleRepository
import com.saludaunclic.semefa.regafi.repository.LibErrorRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ErrorsService(
    private val fieldErrorRepository: FieldErrorRepository,
    private val fieldErrorRuleRepository: FieldErrorRuleRepository,
    private val libErrorRepository: LibErrorRepository
) {
    companion object {
        val lock = Object()
    }

    private lateinit var fieldErrorMap: Map<Int, String>
    private lateinit var fieldErrorRuleMap: Map<Int, String>
    private lateinit var libErrorMap: Map<Int, String>

    fun getFieldError(id: Int): String? = fieldErrorMap[id]

    fun getFieldErrorRule(id: Int): String? = fieldErrorRuleMap[id]

    fun getLibError(id: Int): String? = libErrorMap[id]

    @PostConstruct
    fun reset() {
        synchronized(lock) {
            fieldErrorMap = fieldErrorRepository.findAll().associate { it.id to it.name }
            fieldErrorRuleMap = fieldErrorRuleRepository.findAll().associate { it.id to it.description }
            libErrorMap = libErrorRepository.findAll().associate { it.id to it.description }
        }
    }
}

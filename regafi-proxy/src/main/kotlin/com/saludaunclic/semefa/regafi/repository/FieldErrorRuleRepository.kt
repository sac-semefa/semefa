package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.FieldErrorRule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldErrorRuleRepository: CrudRepository<FieldErrorRule, Int>

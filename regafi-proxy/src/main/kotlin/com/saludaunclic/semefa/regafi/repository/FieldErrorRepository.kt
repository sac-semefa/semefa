package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.FieldError
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldErrorRepository: CrudRepository<FieldError, Int>

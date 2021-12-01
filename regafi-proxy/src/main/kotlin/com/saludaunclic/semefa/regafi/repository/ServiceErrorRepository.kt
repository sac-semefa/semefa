package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.ServiceError
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceErrorRepository: CrudRepository<ServiceError, Int>

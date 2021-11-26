package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.LibError
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LibErrorRepository: CrudRepository<LibError, Int>

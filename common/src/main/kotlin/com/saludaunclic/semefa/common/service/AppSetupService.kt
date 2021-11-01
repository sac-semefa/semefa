package com.saludaunclic.semefa.common.service

import com.saludaunclic.semefa.common.dto.AppSetupDto
import com.saludaunclic.semefa.common.mapper.UserMapper
import com.saludaunclic.semefa.common.model.Role
import com.saludaunclic.semefa.common.model.UserStatus
import com.saludaunclic.semefa.common.throwing.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AppSetupService(private val userService: UserService,
                      private val userMapper: UserMapper) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun setupApp(appSetup: AppSetupDto): String {
        if (userService.userCount() > 0) {
            throw ServiceException("Aplicación ya tiene usuario inicial definido", HttpStatus.CONFLICT)
        }

        logger.info("Attempting to setup application with user: $appSetup")
        val user = userService.save(userMapper.toModel(appSetup.user).apply {
            roles = setOf(Role(name = Role.SUPER_ROLE))
            status = UserStatus.ENABLED
        })

        return """Aplicación fue inicializada con:
            $user
        """.trimMargin()
    }
}

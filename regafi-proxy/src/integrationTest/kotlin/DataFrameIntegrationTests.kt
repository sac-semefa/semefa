import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.common.dto.AppSetupDto
import com.saludaunclic.semefa.common.repository.UserRepository
import com.saludaunclic.semefa.common.service.AppSetupService
import com.saludaunclic.semefa.regafi.RegafiProxyApp
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate
import util.TestDataUtils

@ContextConfiguration
@SpringBootTest(classes = [ RegafiProxyApp::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataFrameIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val appSetupService: AppSetupService,
    @Autowired val userRepository: UserRepository,
    @Autowired val objectMapper: ObjectMapper
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val appSetup: AppSetupDto = TestDataUtils.generateAppSetup()
    lateinit var token: String

    @BeforeAll
    fun setup() {
        userRepository.deleteAll()
        with(appSetup.user) {
            appSetupService.setupApp(appSetup)
            token = TestDataUtils.loginWithCredentials(restTemplate, username, password).getBody()?.token ?: ""
        }
    }

    @Test
    fun `Assert post a Regafi 271 payload`() {
        val payload = TestDataUtils.generateIn271RegafiUpdate()
        logger.info("Processing payload\n${objectMapper.writeValueAsString(payload)}")
        val headers: HttpHeaders = HttpHeaders().apply {
            set(HttpHeaders.AUTHORIZATION, "Bearer $token")
            set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
        val request: HttpEntity<In271RegafiUpdate> = HttpEntity(payload, headers)
        val entity = restTemplate.postForEntity("/api/regafi/update271", request, In997RegafiUpdate::class.java)
        assertNotNull(entity)
    }

    @AfterAll
    fun tearDown() {
        userRepository.deleteAll()
    }
}

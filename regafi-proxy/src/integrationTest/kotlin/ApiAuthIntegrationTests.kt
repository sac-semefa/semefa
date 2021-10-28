import com.saludaunclic.semefa.regafi.GatewayApplication
import com.saludaunclic.semefa.regafi.dto.AppSetupDto
import com.saludaunclic.semefa.regafi.repository.UserRepository
import com.saludaunclic.semefa.regafi.service.setup.AppSetupService
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import util.TestDataUtils

@ContextConfiguration
@SpringBootTest(classes = [ GatewayApplication::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiAuthIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val appSetupService: AppSetupService,
    @Autowired val userRepository: UserRepository
) {
    val appSetup: AppSetupDto = TestDataUtils.generateAppSetup()

    @BeforeAll
    fun setup() {
        appSetupService.setupApp(appSetup)
    }

    @Test
    fun `Assert user can authenticate`() {
        with(appSetup.user) {
            val entity = TestDataUtils.loginWithCredentials(restTemplate, username, password)
            assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        }
    }

    @Test
    fun `Assert user cannot authenticate`() {
        val entity = restTemplate.postForEntity(
            "/api/public/auth/login?username=otherUser&password=somePassword",
            null,
            String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @AfterAll
    fun tearDown() {
        userRepository.deleteAll()
    }
}

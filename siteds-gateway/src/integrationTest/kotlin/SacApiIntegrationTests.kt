import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.SitedsGatewayApp
import com.saludaunclic.semefa.siteds.model.ResponseInConProc271
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import java.util.Collections

@ContextConfiguration
@SpringBootTest(classes = [ SitedsGatewayApp::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SacApiIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val objectMapper: ObjectMapper
) {
    @Test
    fun `Test rest template`() {
        val inConAse270 = objectMapper.readValue("""
{
  "noTransaccion" : "270_CON_ASE",
  "idRemitente" : "00030009",
  "idReceptor" : "30009",
  "feTransaccion" : "20221230",
  "hoTransaccion" : "131255",
  "idCorrelativo" : "000000001",
  "idTransaccion" : "270",
  "tiFinalidad" : "13",
  "caRemitente" : "2",
  "nuRucRemitente" : "20602157858",
  "txRequest" : "CN",
  "caReceptor" : "2",
  "caPaciente" : "1",
  "apPaternoPaciente" : "",
  "noPaciente" : "",
  "coAfPaciente" : "",
  "apMaternoPaciente" : "",
  "tiDocumento" : "1",
  "nuDocumento" : "74443874",
  "coProducto" : "",
  "deProducto" : "",
  "coInProducto" : "Revisión del Instalador 5.5",
  "nuCobertura" : "",
  "deCobertura" : "",
  "caServicio" : "",
  "coCalservicio" : "",
  "beMaxInicial" : "",
  "coTiCobertura" : "",
  "coSuTiCobertura" : "",
  "coAplicativoTx" : "",
  "coEspecialidad" : "",
  "coParentesco" : "",
  "nuPlan" : "",
  "nuAutOrigen" : "",
  "tiAccidente" : " ",
  "feAccidente" : " ",
  "tiCaContratante" : "",
  "noPaContratante" : "",
  "noContratante" : "",
  "noMaContratante" : "",
  "tiDoContratante" : "",
  "idReContratante" : "4A",
  "coReContratante" : ""
}
        """.trimIndent(), InConAse270::class.java)
        val request: HttpEntity<InConAse270> = HttpEntity(inConAse270, headers())
        val entity = restTemplate.postForEntity(
            "http://200.48.13.34/apiSemefa/getConsultaAsegNom",
            request,
            ResponseInConProc271::class.java)
        println("""Response:
            |${objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity.body?.data)}
        """.trimMargin())
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    private fun headers(): HttpHeaders = HttpHeaders()
        .apply {
            this.accept = Collections.singletonList(MediaType.APPLICATION_JSON);
            this.contentType = MediaType.APPLICATION_JSON
            this.set(HttpHeaders.AUTHORIZATION, "8845b698617a0651d32d036b688869s9")
        }
}

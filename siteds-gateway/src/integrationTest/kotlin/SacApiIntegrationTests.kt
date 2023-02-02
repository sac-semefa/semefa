import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.SitedsGatewayApp
import com.saludaunclic.semefa.siteds.model.ResponseIn997ResAut
import com.saludaunclic.semefa.siteds.model.ResponseInConNom271
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
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAut271
import java.util.Collections

@ContextConfiguration
@SpringBootTest(classes = [ SitedsGatewayApp::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SacApiIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val objectMapper: ObjectMapper
) {
    @Test
    fun `Test getConsultaAsegNom`() {
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
  "noPaciente" : "Josue",
  "coAfPaciente" : "",
  "apMaternoPaciente" : "",
  "tiDocumento" : "1",
  "nuDocumento" : "",
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
  "feAccidente" : "",
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
            ResponseInConNom271::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Test getNumAutorizacion`() {
        val inSolAut271 = objectMapper.readValue("""
{
    "noTransaccion": "271_SOL_AUT",
    "idRemitente" : "00030009",
    "idReceptor" : "30009",
    "feTransaccion" : "20230116",
    "hoTransaccion" : "110131",
    "idCorrelativo" : "000000001",
    "idTransaccion": "271",
    "tiFinalidad": "13",
    "caRemitente": "2",
    "nuRucRemitente": "20602157858",
    "caReceptor": "2",
    "coAdmisionista": "9876543abc",
    "caPaciente": "1",
    "apPaternoPaciente": "WARTHON",
    "noPaciente": "DANTE EFRAIN",
    "coAfPaciente": "0000010605",
    "apMaternoPaciente": "GRAJEDA",
    "coEsPaciente": "6",
    "tiDoPaciente": "1",
    "nuDoPaciente": "25627924",
    "nuIdenEmpleador": "20100973473",
    "nuContratoPaciente": "0000000751",
    "nuPoliza": "3134124314",
    "nuCertificado": "1",
    "coTiPolizaAfiliacion": "1",
    "coProducto": "02",
    "deProducto": "descripcion producto",
    "nuPlan": "00743621",
    "tiPlanSalud": "7",
    "coMoneda": "1",
    "coParentesco": "01",
    "soBeneficio": "S",
    "nuSoBeneficio": "01",
    "coEspecialidad": "CO0321",
    "feNacimiento": "19610512",
    "genero": "1",
    "esMarital": "1",
    "feIniVigencia": "20040701",
    "feFinVigencia": "20040701",
    "esCobertura": "1",
    "nuDecAccidente": "ACD100",
    "idInfAccidente": "Acc",
    "deTiAccidente": "1",
    "feAfiliacion": "",
    "feOcuAccidente": "20080321",
    "nuAtencion": "70014",
    "idDerFarmacia": "Far",
    "tiProducto": "2",
    "deProductoDeFarmacia": "FARMA",
    "feAtencion": "20091011",
    "obsCobertura": "Obs",
    "msgObs": "Observaciones",
    "msgConEspeciales": "CONDICIONES ESPECIALES",
    "caContratante": "2",
    "noPaContratante": "ICM PACHAPAQUI SAC",
    "noContratante": "ICM PACHAPAQUI SAC",
    "noMaContratante": "ICM PACHAPAQUI SAC",
    "tiDoContratante": "6",
    "idReContratante": "XX5",
    "coReContratante": "20100973473",
    "caTitular": "1",
    "noPaTitular": "BALLON",
    "noTitular": "ANGELA RITA",
    "coAfTitular": "0000424421",
    "noMaTitular": "ARENAS",
    "tiDoTitular": "6",
    "idReTitular": "XX5",
    "nuDoTitular": "20100973473",
    "feIncTitular": "20040701",
    "nuCobPreExistencia": "4",
    "beMaxInicial": "50",
    "canServicio": "1",
    "idDeProducto": "2",
    "coTiCobertura": "4",
    "coSubTiCobertura": "100",
    "msgObsPre": "OBSERVACIONES",
    "msgConEspecialesPre": "CONDICIONES ESPECIALES",
    "coTiMoneda": "1",
    "coPagoFijo": "60",
    "coCalServicio": "Z3",
    "canCalServicio": "1",
    "coPagoVariable": "0.50",
    "flagCG": "0",
    "deflagCG": "Descripción de C.G",
    "feFinCarencia": "20040701",
    "feFinEspera": "20040701",
    "inSolAutProEsp271Detalle": [
        {
            "coInProcedimiento": "66",
            "coTiProConAmbulatoria": "900152",
            "nuPlanConAmbulatoria": "4",
            "imDeducible": "60",
            "poConAmbulatoria": "0.50",
            "frConAmbulatoria": "20",
            "geConAmbulatoria": "1",
            "caConAmbulatoria": "1",
            "msgConAmbulatoria": "OBSERVACIONES"
        }
    ],
    "inSolAutTieEsp271Detalle": [
        {
            "coTiEspera": "B30.9 ",
            "idTiEspera": "Tes",
            "deTiEspera": "DESCRIPCION TIEMPO DE ESPERA",
            "feFinVigenciaTiEspera": "20040701",
            "msgTiEspera": "OBSERVACIONES TE"
        }
    ],
    "inSolAutExeCar271Detalle": [
        {
            "coExCarencia": "",
            "idExCarencia": "",
            "deExCarencia": "",
            "msgExCarencia": ""
        }
    ],
    "inSolAut271Detalle": [
        {
            "CIE10Restricciones": "A74.0",
            "idRestricciones": "Car",
            "obsRestricciones": "EXCEPCION CARENCIA",
            "deRestricciones": "OBSERVACIONES",
            "msgRestricciones": "CIE10",
            "monTopeRestricciones": "Pr",
            "feFinEsperaRestricciones": "20040701"
        }
    ],
    "caRegafi": "1",
    "noPaRegafi": "BALLON",
    "noRegafi": "ANGELA RITA",
    "coAfRegafi": "0000424421",
    "noMaRegafi": "ARENAS",
    "tiDoRegafi": "1",
    "idReRegafi": "A4",
    "nuDoRegafi": "08834001",
    "feNaRegafi": "19610512",
    "geRegafi": "1",
    "coPaisRegafi": "33 ",
    "nuControl": "",
    "nuControlST": ""
}
        """.trimIndent(), InSolAut271::class.java)
        val request: HttpEntity<InSolAut271> = HttpEntity(inSolAut271, headers())
        val entity = restTemplate.postForEntity(
            "http://200.48.13.34/apiSemefa/getNumAutorizacion",
            request,
            ResponseIn997ResAut::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    private fun headers(): HttpHeaders = HttpHeaders()
        .apply {
            this.accept = Collections.singletonList(MediaType.APPLICATION_JSON);
            this.contentType = MediaType.APPLICATION_JSON
            this.set(HttpHeaders.AUTHORIZATION, "8845b698617a0651d32d036b688869s9")
        }
}

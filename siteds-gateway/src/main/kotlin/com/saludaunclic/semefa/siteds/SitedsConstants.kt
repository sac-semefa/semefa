package com.saludaunclic.semefa.siteds

object SitedsConstants {
    object Transactions {
        const val REQ_278_CON_ENT_VINC: String = "278_CON_ENT_VINC"
        const val RES_278_RES_ENT_VINC: String = "278_RES_ENT_VINC"

        const val REQ_270_CON_ASE: String = "270_CON_ASE"
        const val RES_271_CON_NOM: String = "271_CON_NOM"
        const val RES_271_CON_COD: String = "271_CON_COD"

        const val REQ_270_REGAFI: String = "270_REGAFI"
        const val RES_271_REGAFI: String = "271_REGAFI"

        const val RES_271_RES_SCTR: String = "271_RES_SCTR"

        const val RES_271_RES_DERIVA: String = "271_RES_DERIVA"

        const val RES_271_CON_PROC: String = "271_CON_PROC"

        const val RES_271_CON_DTAD: String = "271_CON_DTAD"

        const val RES_271_CON_MED: String = "271_CON_MED"

        const val RES_271_CON_OBS: String = "271_CON_OBS"

        const val REQ_271_SOL_AUT: String = "271_SOL_AUT"
        const val RES_997_RES_AUT: String = "997_RES_AUT"

        const val REQ_271_LOGACRE_INSERT: String = "271_LOGACRE_INSER"

        const val REQ_278_SOL_CG: String = "278_SOL_CG"
        const val RES_278_RES_CG: String = "278_RES_CG"
    }

    object ErrorCodes {
        const val NO_ERROR: String = "0000"
        const val SYSTEM_ERROR: String = "0020"
        const val TRANSACTION_UNUSED: String = "0100"
        const val USER_MISSING: String = "0110"
        const val EXCEPTION_CODE_MISSING: String = "0200"
        const val EXCEPTION_CODE_INVALID: String = "0210"
        const val TRANSACTION_NAME_MISSING: String = "0300"
        const val TRANSACTION_NAME_INVALID: String = "0310"
        const val IAFA_CODE_MISSING: String = "0400"
        const val IAFA_CODE_INVALID: String = "0410"
        const val ID_REF_INVALID: String = "0401"
    }
}

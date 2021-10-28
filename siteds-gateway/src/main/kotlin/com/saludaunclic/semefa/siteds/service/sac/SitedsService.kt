package com.saludaunclic.semefa.siteds.service.sac

import com.saludaunclic.semefa.siteds.model.BasicSitedsTransaction
import org.springframework.stereotype.Service

@Service
class SitedsService {
    companion object {
        private val transactions: List<BasicSitedsTransaction> = listOf(
            BasicSitedsTransaction("278_CON_ENT_VINC", 278, 13)
        )
        private val codeTransactions = transactions.map { it.transactionCode to it }
    }

}
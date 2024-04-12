package `in`.hypernation.payup.data.manipulation

import `in`.hypernation.payup.utils.RUPEE_SYMBOL

class StringManipulation {

    fun getBankName(message: String): String? {
        val text = message.trimIndent()
        val bankNameRegex = Regex("Select Option: (.+)")
        val matchResult = bankNameRegex.find(text)
        return matchResult?.groups?.get(1)?.value?.trim()
    }

    fun getBankBalance(message: String): String? {
        val balanceRegex = Regex("Rs\\.([0-9,.]+)")
        val matchResult = balanceRegex.find(message)
        val balanceString = matchResult?.groups?.get(1)?.value
        val bankBalance = balanceString?.replace(",", "")?.toDoubleOrNull()
        var balanceS : String? = null
        if(bankBalance != null){
            balanceS = "$RUPEE_SYMBOL $bankBalance"
        }
        return balanceS
    }
}
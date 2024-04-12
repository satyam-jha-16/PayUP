package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.Account

interface HomeRepository {

    fun linkAccount(simSlot : Int, onResponse : (USSDResponse<Account>) -> Unit)
    fun checkBalance(simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit)
}
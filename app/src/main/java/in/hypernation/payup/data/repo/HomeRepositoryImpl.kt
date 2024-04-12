package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.manipulation.StringManipulation
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.utils.BYPASS_LANGUAGE
import `in`.hypernation.payup.utils.OPTION_VIEW
import `in`.hypernation.payup.utils.USSD_CODE
import timber.log.Timber

class HomeRepositoryImpl (
    private val ussdApi : USSDApi,
    private val context: Context,
    private val stringManipulation: StringManipulation
) : HomeRepository {
    override fun linkAccount(simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit) {
        ussdApi.callUSSDRequest(context, USSD_CODE, simSlot, object : USSDBuilder.CallBack{
            override fun response(message: String, isError : Boolean) {
                // First time shows Welcome View
                Timber.d(message)
                if(message.contains(BYPASS_LANGUAGE)){
                    ussdApi.send("1"){}
                } else if(message.contains(OPTION_VIEW)) {
                    ussdApi.cancel()
                    // String Bank name
                    val bankName = stringManipulation.getBankName(message)
                    onResponse(USSDResponse.Success(Account(bankName, null, true)))

                } else {
                    onResponse(USSDResponse.Error(message = "Can't Linked Right Now"))
                }
            }

            override fun over(message: String, isError: Boolean) {
                // Message = Check your accessibility
                // USSD Not Good
                //String -> Balance
                Timber.d(message)
                if(isError){
                    onResponse(USSDResponse.Error(message = "Something Went Wrong"))
                }
                val bankBalance = stringManipulation.getBankBalance(message)
                onResponse(USSDResponse.Success(Account(null, bankBalance = bankBalance, true)))
            }
        })

    }

    override fun checkBalance(simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit) {
        ussdApi.callUSSDRequest(context, USSD_CODE, simSlot, object : USSDBuilder.CallBack{
            override fun response(message: String, isError : Boolean) {
                Timber.d(message)
                if(message.contains(OPTION_VIEW)){
                    ussdApi.send("3"){
                        Timber.d("Balance -> $it")
                    }
                }
            }

            override fun over(message: String, isError : Boolean) {
                Timber.d(message)
                if(isError){
                    onResponse(USSDResponse.Error(message = "Can't fetch balance"))
                }
                val bankBalance = stringManipulation.getBankBalance(message)
                onResponse(USSDResponse.Success(Account(bankBalance = bankBalance, isLinked = true, bankName = null)))

            }

        })
    }
}
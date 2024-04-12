package `in`.hypernation.payup.data.USSD

import android.content.Context

interface USSDApi {
    fun send(text : String, callBackMessage:(String) -> Unit)
    fun cancel()
    fun callUSSDRequest(context: Context, code: String, simSlot: Int, callBack:USSDBuilder.CallBack)
    fun verifyAccessibilityAccess(context: Context):Boolean
    fun setDefault(isDefault : Boolean)
}
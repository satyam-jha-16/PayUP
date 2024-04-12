package `in`.hypernation.payup.data.local

import android.content.Context

class PreferenceManager(private val context: Context) {
    private val sf = context.getSharedPreferences("PayUpSf", Context.MODE_PRIVATE)

    // Keys
    private val isLinkKey = "IsAccountLink"
    private val bankNameKey = "BankName"

    fun getAccountLinkStatus() : Boolean{
        return sf.getBoolean(isLinkKey, false)
    }

    fun setAccountLinkStatus(value : Boolean) {
        sf.edit().putBoolean(isLinkKey, value).apply()
    }

    fun getBankName() : String?{
        return sf.getString(bankNameKey, null)
    }

    fun setBankName(value : String?) {
        sf.edit().putString(bankNameKey, value).apply()
    }

}
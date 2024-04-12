package `in`.hypernation.payup.data.USSD

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.telecom.TelecomManager
import android.view.accessibility.AccessibilityManager
import `in`.hypernation.payup.data.USSD.USSDApi
import timber.log.Timber


@SuppressLint("StaticFieldLeak")
object USSDBuilder : USSDApi, USSDInterface {

    private val simSlotName = arrayOf("extra_asus_dial_use_dualsim",
        "com.android.phone.extra.slot", "slot", "simslot", "sim_slot", "subscription",
        "Subscription", "phone", "com.android.phone.DialingMode", "simSlot", "slot_id",
        "simId", "simnum", "phone_type", "slotId", "slotIdx")

    val map : HashMap<String, List<String>> = hashMapOf(
        "BYPASS1" to listOf("Select Language", "Language"),
        "BYPASS0" to listOf("Select Option"),
        "LOGIN" to listOf("WELCOME", "bank's name", "UPI PIN", "Welcome"),
        "ERROR" to listOf("timed out", "Timed", "timed", "Timed out"),
        "FINAL" to listOf("account balance")
    )

    lateinit var context : Context
        private set

    lateinit var callBack : CallBack

    var isRunning : Boolean? = false
        private set

    var isDefault: Boolean? = false
        private set
    var sendType : Boolean? = false
        private set

    var callBackMessage:((String)->Unit)? = null
        private set

    private var ussdInterface : USSDInterface? = null;

    init {
        ussdInterface = this
    }



    override fun send(text: String, callBackMessage: (String) -> Unit) {
        this.callBackMessage = callBackMessage
        sendType = true
        ussdInterface?.sendData(text)
    }

    override fun cancel() {
        isRunning = false
        USSDServiceKT.cancel()
    }

    @SuppressLint("MissingPermission")
    override fun callUSSDRequest(context: Context, code: String, simSlot: Int, callBack: CallBack) {
        sendType = false
        this.context = context
        this.callBack = callBack
        Timber.d(verifyAccessibilityAccess(context).toString())
        if (verifyAccessibilityAccess(context)) {
            dialUp(code, simSlot)
            Timber.d("Dial UP")
        } else {
            callBack.over("Check your accessibility", isError = true)
        }

    }

    override fun verifyAccessibilityAccess(context: Context): Boolean  =
        isAccessibilityServicesEnable(context).also {
            if (!it) openSettingsAccessibility(context as Activity)
        }

    override fun setDefault(isDefault: Boolean) {
        this.isDefault = isDefault
    }

    override fun sendData(text: String) = USSDServiceKT.send(text)

    override fun stopRunning() {
        isRunning = false
    }

    interface CallBack{
        fun response(message: String, isError : Boolean)
        fun over(message: String, isError: Boolean)
    }

    private fun dialUp(ussdPhoneNumber: String, simSlot: Int) {
        when {
            !map.containsKey("LOGIN") || !map.containsKey("ERROR") || !map.containsKey("BYPASS1") ->
                callBack.over("Bad Mapping structure", isError = true)
            ussdPhoneNumber.isEmpty() -> callBack.over("Bad ussd number", isError = true)
            else -> {
                var phone = Uri.encode("#")?.let {
                    ussdPhoneNumber.replace("#", it)
                }
                isRunning = true
                context.startActivity(getActionCallIntent(Uri.parse("tel:$phone"), simSlot))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getActionCallIntent(uri: Uri?, simSlot: Int): Intent {
        val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as? TelecomManager
        return Intent(Intent.ACTION_CALL, uri).apply {
            simSlotName.map { sim -> putExtra(sim, simSlot) }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("com.android.phone.force.slot", true)
            putExtra("Cdma_Supp", true)
            telecomManager?.callCapablePhoneAccounts?.let { handles ->
                if (handles.size > simSlot)
                    putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", handles[simSlot])
            }
        }
    }

    private fun isAccessibilityServicesEnable(context: Context): Boolean {
        Timber.d(context.packageName)
        Timber.d(Settings.Secure.getInt(context.applicationContext.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED).toString())
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).apply {
            installedAccessibilityServiceList.forEach { service ->
                Timber.d(service.id)
                Timber.d(service.id.contains(context.packageName).toString())
                if (service.id.contains(context.packageName) &&
                    Settings.Secure.getInt(context.applicationContext.contentResolver,
                        Settings.Secure.ACCESSIBILITY_ENABLED) == 1) {
                    Timber.d(Settings.Secure.getString(context.applicationContext.contentResolver,
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES))
                    Settings.Secure.getString(context.applicationContext.contentResolver,
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)?.let {
                            Timber.d(it.contains(service.id).toString())
                        if (it.contains(context.packageName)) return true
                    }
                }

            }
        }
        return false

    }

    private fun openSettingsAccessibility(activity: Activity) =
        with(AlertDialog.Builder(activity)) {
            setTitle("USSD Accessibility permission")
            setMessage("You must enable accessibility permissions for the app %s".format(getNameApp(activity)))
            setCancelable(true)
            setNeutralButton("ok") { _, _ ->
                activity.startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
            }
            create().show()
        }

    private fun getNameApp(activity: Activity): String = when (activity.applicationInfo.labelRes) {
        0 -> activity.applicationInfo.nonLocalizedLabel.toString()
        else -> activity.getString(activity.applicationInfo.labelRes)
    }
}
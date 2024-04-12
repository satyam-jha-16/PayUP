package `in`.hypernation.payup.data.USSD

interface USSDInterface {
    fun sendData(text: String)
    fun stopRunning()
}
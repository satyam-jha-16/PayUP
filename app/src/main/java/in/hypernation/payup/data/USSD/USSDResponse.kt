package `in`.hypernation.payup.data.USSD

sealed class USSDResponse<T>(val data : T? = null, val message : String? = null) {
    class Success<T>(data: T) : USSDResponse<T>(data)
    class Error<T>(data: T? = null, message: String) : USSDResponse<T>(data = data,message = message)
}
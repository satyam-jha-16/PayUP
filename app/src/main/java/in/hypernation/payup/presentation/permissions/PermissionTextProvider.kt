package `in`.hypernation.payup.presentation.permissions

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined : Boolean) : String
}
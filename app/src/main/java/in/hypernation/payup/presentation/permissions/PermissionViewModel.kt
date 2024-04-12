package `in`.hypernation.payup.presentation.permissions

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class PermissionViewModel : ViewModel() {

    val visibleDialogQueue = mutableStateListOf<String>()

    fun dismissDialogQueue(){
        visibleDialogQueue.removeFirst()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean){

        if(!isGranted && !visibleDialogQueue.contains(permission)) addPermissionOnQueue(permission)

    }
    private fun addPermissionOnQueue(permission : String) {
        visibleDialogQueue.add(permission)
    }

}
package `in`.hypernation.payup.presentation.home

import `in`.hypernation.payup.data.models.Account

data class LinkState(
    val account : Account = Account(isLinked = false, bankBalance = null, bankName = null),
    val message : String? = null,
    val isLoading : Boolean = true
)

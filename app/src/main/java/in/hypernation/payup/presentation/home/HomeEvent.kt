package `in`.hypernation.payup.presentation.home

sealed class HomeEvent {

    data class Linked(val simSlot: Int) : HomeEvent()
    data object OnPayWithQR : HomeEvent()
    data object OnPayWithUPI : HomeEvent()
    data object OnCheckBalance : HomeEvent()

}
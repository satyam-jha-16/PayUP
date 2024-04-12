package `in`.hypernation.payup.di

import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.local.PreferenceManager
import `in`.hypernation.payup.data.manipulation.StringManipulation
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.data.repo.HomeRepositoryImpl
import `in`.hypernation.payup.presentation.home.HomeViewModel
import `in`.hypernation.payup.presentation.permissions.PermissionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { PermissionViewModel() }
    single<USSDApi> {USSDBuilder}
    single<HomeRepository> {HomeRepositoryImpl(get(), get(), get())}
    single {StringManipulation()}
    single { PreferenceManager(get()) }
}
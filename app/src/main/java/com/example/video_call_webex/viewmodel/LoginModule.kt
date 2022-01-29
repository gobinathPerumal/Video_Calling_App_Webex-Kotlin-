package com.example.video_call_webex.viewmodel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { LoginViewModel(get(), get()) }

    single { LoginRepository() }
}
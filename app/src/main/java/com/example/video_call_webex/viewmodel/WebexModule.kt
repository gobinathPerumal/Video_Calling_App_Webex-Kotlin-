package com.example.video_call_webex.viewmodel

import com.example.video_call_webex.webexcall.RingerManager
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val webexModule = module(createdAtStart = true) {
    single { WebexRepository(get()) }
    single { RingerManager(get()) }

    viewModel {
        WebexViewModel(get(), get())
    }
}
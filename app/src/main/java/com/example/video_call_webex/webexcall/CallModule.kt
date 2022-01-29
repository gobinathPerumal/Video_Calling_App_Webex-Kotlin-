package com.example.video_call_webex.webex.webexcall

import com.example.video_call_webex.webexcall.CallViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val callModule = module {
    viewModel {
        CallViewModel(get())
    }
}
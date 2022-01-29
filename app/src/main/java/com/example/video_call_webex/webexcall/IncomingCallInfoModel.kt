package com.example.video_call_webex.webexcall

import com.ciscowebex.androidsdk.phone.Call

abstract class IncomingCallInfoModel(var call: Call?) {
    var isEnabled: Boolean = true
}
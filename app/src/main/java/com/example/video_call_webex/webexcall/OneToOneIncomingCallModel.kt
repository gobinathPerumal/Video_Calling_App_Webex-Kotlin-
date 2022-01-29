package com.example.video_call_webex.webexcall

import com.ciscowebex.androidsdk.phone.Call
import com.example.video_call_webex.webexcall.IncomingCallInfoModel

data class OneToOneIncomingCallModel(val _call: Call?): IncomingCallInfoModel(_call)
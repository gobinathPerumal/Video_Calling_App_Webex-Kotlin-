package com.example.video_call_webex.viewmodel

import com.ciscowebex.androidsdk.Webex
import com.ciscowebex.androidsdk.auth.OAuthWebViewAuthenticator
import com.ciscowebex.androidsdk.auth.Authenticator
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val OAuthWebexModule = module {
    single <Authenticator> (named("oAuth")) {
        val clientId = ""
        val clientSecret = ""
        val additionalScopes = ""
        val redirectUri = ""
        val email = ""
        OAuthWebViewAuthenticator(clientId, clientSecret, additionalScopes, redirectUri, email)
    }

    factory {
        Webex(androidApplication(), get(named("oAuth")))
    }
}
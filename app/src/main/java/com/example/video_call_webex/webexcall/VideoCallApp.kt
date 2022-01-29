package com.example.video_call_webex.webexcall

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.video_call_webex.person.personModule
import com.example.video_call_webex.viewmodel.JWTWebexModule
import com.example.video_call_webex.viewmodel.OAuthWebexModule
import com.example.video_call_webex.viewmodel.loginModule
import com.example.video_call_webex.viewmodel.webexModule
import com.example.video_call_webex.webex.webexcall.callModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules


class VideoCallApp : Application(), LifecycleObserver {

    companion object {
        lateinit var instance: VideoCallApp
            private set

        fun applicationContext(): Context {
            return instance.applicationContext
        }

        fun get(): VideoCallApp {
            return instance
        }

        var inForeground: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@VideoCallApp)
        }
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        // app moved to foreground
        inForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        // app moved to background
        inForeground = false
    }

    fun closeApplication() {
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun loadKoinModules(isOAuthLogin: Boolean) {
        if (isOAuthLogin) {
            loadKoinModules(
                listOf(
                    webexModule,
                    loginModule,
                    OAuthWebexModule,
                    callModule,
                    personModule
                )
            )
        } else {
            loadKoinModules(
                listOf(
                    webexModule,
                    loginModule,
                    JWTWebexModule,
                    callModule,
                    personModule
                )
            )
        }
    }

    fun unloadKoinModules() {
        unloadKoinModules(
            listOf(
                webexModule,
                loginModule,
                JWTWebexModule,
                OAuthWebexModule,
                callModule,
                personModule
            )
        )
    }

}
package com.example.video_call_webex.webexcall

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.video_call_webex.R
import com.example.video_call_webex.databinding.ActivityCallBinding
import com.example.video_call_webex.utils.ValueConstant
import com.example.video_call_webex.viewmodel.WebexViewModel

/*
* @CallActivity is used to connect call in webex.
*/
class CallActivity : WebExBaseActivity() {

    lateinit var binding: ActivityCallBinding

    companion object {
        var webexMeetingPin : String ?= null
        var isHost : Boolean ?= false
        fun getOutgoingIntent(context: Context, callerName: String, meetingPin: String, userType: String?): Intent {
            val intent = Intent(context, CallActivity::class.java)
            intent.putExtra(ValueConstant.CALLING_ACTIVITY_ID, 0)
            intent.putExtra(ValueConstant.OUTGOING_CALL_CALLER_ID, callerName)
            intent.putExtra(ValueConstant.MEETING_PIN, meetingPin)
            intent.putExtra(ValueConstant.USER_TYPE, userType)
            return intent
        }

        fun getIncomingIntent(context: Context): Intent {
            val intent = Intent(context, CallActivity::class.java)
            intent.putExtra(ValueConstant.CALLING_ACTIVITY_ID, 1)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag = "CallActivity"
        webexMeetingPin = "";
        DataBindingUtil.setContentView<ActivityCallBinding>(this, R.layout.activity_call)
            .also { binding = it }
            .apply {
                val callingActivity = intent.getIntExtra(ValueConstant.CALLING_ACTIVITY_ID, 0)
                if (callingActivity == 0) {
                    val callerId = intent.getStringExtra(ValueConstant.OUTGOING_CALL_CALLER_ID)
                    val meetingPin = intent.getStringExtra(ValueConstant.MEETING_PIN)
                    val userType = intent.getStringExtra(ValueConstant.USER_TYPE)
                    isHost = !userType.equals("1")
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.containerFragment) as CallControlsFragment
                    callerId?.let {
                        meetingPin?.let { it1 ->
                            webexMeetingPin = it1
                            if(isHost as Boolean) {
                                fragment.dialOutgoingCall(callerId, isHost!!, webexMeetingPin!!)
                            }else
                            {
                                fragment.dialOutgoingCall(callerId)
                            }}
                    }
                }
            }
    }

    override fun onBackPressed() {
        /*val fragment = supportFragmentManager.findFragmentById(R.id.containerFragment)
        if ( (fragment is CallControlsFragment) && (fragment.needBackPressed())) {
            fragment.onBackPressed()
        } else {
            super.onBackPressed()
        }*/
    }

    fun alertDialog(shouldFinishActivity: Boolean, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.call_failed))
        builder.setMessage(resources.getString(R.string.oops_meeting_disconnected))

        builder.setPositiveButton("OK") { _, _ ->
            if (shouldFinishActivity) finish()
        }

        builder.show()
    }

    private fun toBeShownOnLockScreen() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true)
            setShowWhenLocked(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            )
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        toBeShownOnLockScreen()
    }
}
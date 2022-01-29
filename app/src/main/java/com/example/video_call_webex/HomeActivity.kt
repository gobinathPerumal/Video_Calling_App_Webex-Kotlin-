package com.example.video_call_webex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.video_call_webex.webexcall.VideoCallApp
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        var email: String? = null
        var jwtToken: String? = null
        var sipAddress: String? = null
        var meetingPin: String? = null
        var isGuestUser: Boolean? = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnSwitchLogin.setOnClickListener {
            if (btnSwitchLogin.text != "Join_As_Webex_Login_User") {
                btnSwitchLogin.setText("Join_As_Webex_Login_User")
                ilEmail.visibility = View.GONE
                iltoken.visibility = View.VISIBLE
            } else {
                btnSwitchLogin.setText("Join_as_guest")
                ilEmail.visibility = View.VISIBLE
                iltoken.visibility = View.GONE
            }
        }
        btnLogin.setOnClickListener {
            (application as VideoCallApp).unloadKoinModules()
            val intent = Intent(this, WebExLoginActivity::class.java)
            sipAddress = ""
            meetingPin = ""
            if (btnSwitchLogin.text != "Join_As_Webex_Login_User") {
                if (!etEmail.text.isNullOrEmpty()) {
                    (application as VideoCallApp).loadKoinModules(true)
                    email = etEmail.text.toString()
                    isGuestUser = false
                    startActivity(intent)
                } else {
                    etEmail.error = "This Filed is Required"
                }
            } else {
                if (!etToken.text.isNullOrEmpty()) {
                    (application as VideoCallApp).loadKoinModules(false)
                    jwtToken = etToken.text.toString()
                    isGuestUser = true
                    startActivity(intent)
                } else {
                    etToken.error = "This Filed is Required"
                }
            }
        }
    }
}
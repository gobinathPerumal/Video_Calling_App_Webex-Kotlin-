package com.example.video_call_webex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.example.video_call_webex.databinding.ActivityWebexLoginBinding
import com.example.video_call_webex.viewmodel.LoginViewModel
import com.example.video_call_webex.webexcall.CallActivity

/*
* @WebExLoginActivity is used to login webex using webview.
*/
class WebExLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebexLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityWebexLoginBinding>(
            this,
            R.layout.activity_webex_login
        )
            .also { binding = it }
            .apply {

                progressLayout.visibility = View.VISIBLE
                loginViewModel.isAuthorized.observe(
                    this@WebExLoginActivity,
                    Observer { isAuthorized ->
                        progressLayout.visibility = View.GONE
                        isAuthorized?.let {
                            if (it) {
                                statusText.text = getString(R.string.call_connect)
                                progressHorizontal.setProgress(75, true)
                                onLoggedIn()
                            } else {
                                onLoginFailed()
                            }
                        }
                    })

                loginViewModel.isAuthorizedCached.observe(
                    this@WebExLoginActivity,
                    Observer { isAuthorizedCached ->
                        progressLayout.visibility = View.GONE
                        isAuthorizedCached?.let {
                            if (it) {
                                progressHorizontal.setProgress(75, true)
                                statusText.text = getString(R.string.call_connect)
                                onLoggedIn()
                            } else {
                                progressHorizontal.setProgress(50, true)
                                statusText.text = getString(R.string.call_connect)
                                progressLayout.visibility = View.GONE
                                binding.exitButton.visibility = View.GONE
                                loginFailedTextView.visibility = View.GONE
                                webexLoginLayout.visibility = View.VISIBLE
                                loginWebview.visibility = View.VISIBLE
                                if (HomeActivity.isGuestUser == false) {
                                    loginViewModel.authorizeOAuth(loginWebview)
                                } else {
                                    HomeActivity.jwtToken
                                        ?.let { it1 ->
                                            loginViewModel.loginWithJWT(
                                                it1
                                            )
                                        }
                                }
                            }
                        }
                    })

                loginViewModel.errorData.observe(this@WebExLoginActivity, Observer { errorMessage ->
                    onLoginFailed(errorMessage)
                })

                exitButton.setOnClickListener {
                    finish()
                }

                loginViewModel.initialize()
            }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun onLoggedIn() {
        if (!HomeActivity.sipAddress
                .isNullOrEmpty() && !HomeActivity.sipAddress.isNullOrEmpty()
        ) {
            if (HomeActivity.isGuestUser == true) {
                startActivity(
                    CallActivity.getOutgoingIntent(
                        this@WebExLoginActivity,
                        HomeActivity.sipAddress.orEmpty(),
                        HomeActivity.meetingPin.orEmpty(),
                        "1"
                    )
                )
            } else {
                startActivity(
                    CallActivity.getOutgoingIntent(
                        this@WebExLoginActivity,
                        HomeActivity.sipAddress.orEmpty(),
                        HomeActivity.meetingPin.orEmpty(),
                        "0"
                    )
                )
            }
        } else {
            Log.d("msg:::::::::", getString(R.string.webex_meeting_details_not_available))
        }
        finish()
        Log.d("onLoggedIn", "success")
    }

    private fun onLoginFailed(failureMessage: String = getString(R.string.webex_login_failed)) {
        Log.d("auth : ", "onLoginFailed, updating ui")
        binding.loginWebview.visibility = View.GONE
        binding.webexLoginLayout.visibility = View.GONE
        binding.exitButton.visibility = View.VISIBLE
        binding.loginFailedTextView.visibility = View.VISIBLE
        binding.loginFailedTextView.text = failureMessage
    }
}
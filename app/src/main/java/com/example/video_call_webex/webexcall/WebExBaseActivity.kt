package com.example.video_call_webex.webexcall

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.video_call_webex.R
import com.example.video_call_webex.viewmodel.WebexViewModel
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel

open class WebExBaseActivity : AppCompatActivity() {
    var tag = "WebExBaseActivity"
    val webexViewModel: WebexViewModel by viewModel()

    fun showErrorDialog(errorMessage: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.error_occurred)
        val message = TextView(this)
        message.setPadding(10, 10, 10, 10)
        message.text = errorMessage

        builder.setView(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}
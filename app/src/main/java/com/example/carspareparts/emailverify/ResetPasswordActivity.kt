package com.example.carspareparts.emailverify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carspareparts.MainActivity
import com.example.carspareparts.R
import com.example.carspareparts.isValidEmailAddress
import com.example.carspareparts.login.LoginActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        resetPasswordBtn.setOnClickListener {
            if(!emailValidation()){
                ParseUser.requestPasswordResetInBackground(emailResetEditText.text.toString()
                ) {
                    if(it==null){
                        Intent(this, LoginActivity::class.java).apply { startActivity(this) }
                        finish()
                    }
                }
            }
        }
    }

    private fun emailValidation(): Boolean {
        if (emailResetEditText.text.isNullOrEmpty() || (!emailResetEditText.text.toString().isValidEmailAddress())) {
            emailResetLayout.isErrorEnabled = true
            emailResetLayout.error = "Invalid Email."
            return true
        } else
            emailResetLayout.isErrorEnabled = false
        return false
    }

}

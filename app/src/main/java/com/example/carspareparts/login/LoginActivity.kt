package com.example.carspareparts.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carspareparts.MainActivity
import com.example.carspareparts.R
import com.example.carspareparts.emailverify.ResetPasswordActivity
import com.example.carspareparts.signup.SignUpActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        forgetPasswordTextView.setOnClickListener {
            Intent(this, ResetPasswordActivity::class.java).apply {
                startActivity(this)
            }
        }
            signUpTextView.setOnClickListener {
                Intent(this, SignUpActivity::class.java).apply {
                startActivity(this)
            }
            }
        loginBtn.setOnClickListener {
            if(emailValidation()||passwordValidation()) return@setOnClickListener
            ParseUser.logInInBackground(userNameLoginEditText.text.toString()
                ,passwordLoginEditText.text?.toString()) { user, e ->
                if(e==null){
                 Intent(this, MainActivity::class.java).apply { startActivity(this) }
                    finish()
                }
            }
            }

    }
    private fun passwordValidation(): Boolean {
        if (passwordLoginEditText.text.isNullOrEmpty()) {
            passwordLoginLayout.isErrorEnabled = true
            passwordLoginLayout.error = "Please write you password."
            return true
        } else
            passwordLoginLayout.isErrorEnabled = false
        return false
    }

    private fun emailValidation(): Boolean {
        if (userNameLoginEditText.text.isNullOrEmpty()) {
            userNameLoginLayout.isErrorEnabled = true
            userNameLoginLayout.error = "Please write your user name"
            return true
        } else
            userNameLoginLayout.isErrorEnabled = false
        return false
    }
}

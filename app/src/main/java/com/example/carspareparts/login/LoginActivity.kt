package com.example.carspareparts.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.main.MainActivity
import com.example.carspareparts.R
import com.example.carspareparts.User
import com.example.carspareparts.resetpassword.ResetPasswordActivity
import com.example.carspareparts.signup.SignUpActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val user= ParseUser.getCurrentUser()
        if (user!=null){
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel::class.java)
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
            loginViewModel.userLogin(User(userNameLoginEditText.text.toString(),
                passwordLoginEditText.text.toString(),null))

            }
        loginViewModel.loginResult().observe(this, Observer {
            if (it==null){
                Intent(this, MainActivity::class.java).apply { startActivity(this) }
                finish()
            }
            else
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
        })

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

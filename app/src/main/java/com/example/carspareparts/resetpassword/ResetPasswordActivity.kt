package com.example.carspareparts.resetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.carspareparts.R
import com.example.carspareparts.isValidEmailAddress
import com.example.carspareparts.login.LoginActivity
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var resetPasswordViewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        resetPasswordViewModel= ResetPasswordViewModel()
        resetPasswordBtn.setOnClickListener {
            if(!emailValidation()){
                resetPasswordViewModel.resetPassword(emailResetEditText.text.toString())
                }
            }
        resetPasswordViewModel.resetPasswordResult().observe(this, Observer {
            if(it==null){
                Intent(this, LoginActivity::class.java).apply { startActivity(this) }
                finish()
            }
        })
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

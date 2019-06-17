package com.example.carspareparts.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.main.MainActivity
import com.example.carspareparts.User
import com.example.carspareparts.isValidEmailAddress
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    lateinit var signUpViewModel:SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.carspareparts.R.layout.activity_sign_up)
        signUpViewModel= ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        signUpBtn.setOnClickListener {
            if (userNameValidation()|| emailValidation()|| passwordValidation()||
                passwordConfirmationValidation()||passwordMatcherValidation())
                return@setOnClickListener

                signUpViewModel.userSignUp(
                    User(
                        userNameSignUpEditText.text.toString(),
                        passwordConfirmSignUpEditText.text.toString(),
                        emailSignUpEditText.text.toString()
                    )
                )
            }
            signUpViewModel.signUpResult().observe(this, Observer {
                if (it == null) {
                   val intent= Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    }
                else
                  Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

            })
        }

    private fun passwordMatcherValidation(): Boolean {
        if (passwordConfirmSignUpEditText.text.toString() != passwordSignUpEditText.text.toString()) {
            Toast.makeText(this, "password doesn't match!", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    private fun passwordConfirmationValidation(): Boolean {
        if (passwordConfirmSignUpEditText.text.isNullOrEmpty()) {
            passwordConfirmSignUpLayout.isErrorEnabled = true
            passwordConfirmSignUpLayout.error = "Please write you password."
            return true
        } else
            passwordConfirmSignUpLayout.isErrorEnabled = false
        return false
    }

    private fun passwordValidation(): Boolean {
        if (passwordSignUpEditText.text.isNullOrEmpty()) {
            passwordSignUpLayout.isErrorEnabled = true
            passwordSignUpLayout.error = "Please write you password."
            return true
        } else
            passwordSignUpLayout.isErrorEnabled = false
        return false
    }

    private fun emailValidation(): Boolean {
        if (emailSignUpEditText.text.isNullOrEmpty() || (!emailSignUpEditText.text.toString().isValidEmailAddress())) {
            emailSignUpLayout.isErrorEnabled = true
            emailSignUpLayout.error = "Invalid Email."
            return true
        } else
            emailSignUpLayout.isErrorEnabled = false
        return false
    }

    private fun userNameValidation(): Boolean {
        if (userNameSignUpEditText.text.isNullOrEmpty()) {
            userNameSignUpLayout.isErrorEnabled = true
            userNameSignUpLayout.error = "Please write you name."
            return true
        } else
            userNameSignUpLayout.isErrorEnabled = false
        return false
    }

}

package com.example.carspareparts.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carspareparts.MainActivity
import com.example.carspareparts.isValidEmailAddress
import com.parse.FindCallback
import com.parse.ParseRole
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.parse.ParseObject
import com.parse.ParseRelation






class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.carspareparts.R.layout.activity_sign_up)

        signUpBtn.setOnClickListener {
            if (userNameValidation()||
                emailValidation()||
                passwordValidation()||
                passwordConfirmationValidation()||
                passwordMatcherValidation()) return@setOnClickListener


            val user = ParseUser()
            user.username = userNameSignUpEditText.text.toString()
            user.setPassword(passwordConfirmSignUpEditText.text.toString())
            user.email = emailSignUpEditText.text.toString()
            user.signUpInBackground {
                if (it==null){
                    //get user
                    val parseUser=ParseUser.getQuery()
                    parseUser.whereEqualTo("username",userNameSignUpEditText.text.toString())
                    parseUser.getFirstInBackground { user, e ->
                        if (e==null){
                            //get customer row relation
                            val parseRole= ParseRole.getQuery()
                            parseRole.whereEqualTo("name","customer")
                            parseRole.getFirstInBackground { customerRole, e ->
                                if (e==null){
                                    //adding signed in user to role customer relation
                                    customerRole?.users?.add(user)
                                    customerRole?.saveInBackground()
                                }
                            }
                            //add user to customer class
                            val customer = ParseObject("customer")
                            customer.put("user_id", user)
                            customer.put("rating", 1)
                            customer.saveInBackground()

                            Intent(this, MainActivity::class.java).apply { startActivity(this) }
                            finish()
                        }
                        else
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()


                    }
                }
                else
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
            }
        }

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

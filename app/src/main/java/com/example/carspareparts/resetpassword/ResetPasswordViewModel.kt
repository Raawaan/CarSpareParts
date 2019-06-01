package com.example.carspareparts.resetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_reset_password.*

/**
 * Created by rawan on 01/06/19 .
 */
class ResetPasswordViewModel:ViewModel() {
    val resetPasswordLiveData= MutableLiveData<ParseException>()
    fun resetPassword(email:String){
        ParseUser.requestPasswordResetInBackground(email
        ) {
            resetPasswordLiveData.postValue(it)
        }
    }
    fun resetPasswordResult(): MutableLiveData<ParseException> {
       return resetPasswordLiveData
    }
}
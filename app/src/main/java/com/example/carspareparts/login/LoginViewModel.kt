package com.example.carspareparts.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.User
import com.parse.ParseException
import com.parse.ParseUser

/**
 * Created by rawan on 01/06/19 .
 */
class LoginViewModel:ViewModel(){
    val loginLiveData = MutableLiveData<ParseException>()
    fun userLogin(user: User){
        ParseUser.logInInBackground(user.userName,user.password) { user, e ->
            loginLiveData.postValue(e)
        }
    }
    fun loginResult(): MutableLiveData<ParseException> {
        return loginLiveData
    }
}
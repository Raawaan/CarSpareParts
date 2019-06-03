package com.example.carspareparts.main

import androidx.lifecycle.ViewModel
import com.parse.ParseUser

/**
 * Created by rawan on 02/06/19 .
 */
class MainViewModel :ViewModel(){
    fun userSignOut(){
        ParseUser.logOut()
    }
}
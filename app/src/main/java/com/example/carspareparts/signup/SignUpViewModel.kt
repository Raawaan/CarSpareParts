package com.example.carspareparts.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.User
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseRole
import com.parse.ParseUser

/**
 * Created by rawan on 01/06/19 .
 */
class SignUpViewModel:ViewModel(){
    var signUpLiveData = MutableLiveData<ParseException>()
    fun userSignUp(user: User) {
        val parseUser = ParseUser()
        parseUser.username = user.userName
        parseUser.setPassword(user.password)
        parseUser.email = user.email
        parseUser.signUpInBackground {
            signUpLiveData.postValue(it)
            if (it == null) {
                //get customer row relation from role and add user
                saveUserRoleClassCustomerRow()
                //add user to customer class
                saveUserCustomerClass()
            }
        }
    }

    private fun saveUserCustomerClass() {
        val customer = ParseObject("customer")
        customer.put("user_id", ParseUser.getCurrentUser())
        customer.put("rating", 1)
        customer.saveInBackground()
    }

    private fun saveUserRoleClassCustomerRow() {
        val parseRoleQuery = ParseRole.getQuery()
        parseRoleQuery.whereEqualTo("name", "customer")
        parseRoleQuery.getFirstInBackground { customerRole, e ->
            if (e == null) {
                //adding signed in user to role customer relation
                customerRole?.users?.add(ParseUser.getCurrentUser())
                customerRole?.saveInBackground()
            }
        }
    }

    fun signUpResult(): MutableLiveData<ParseException> {
       return signUpLiveData
    }

}
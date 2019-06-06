package com.example.carspareparts.sparepartrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.CustomerRequest
import com.example.carspareparts.User
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.ParseObject
import java.util.*


class RequestViewModel {

    class RequestViewModel: ViewModel(){
        private val requestLiveData = MutableLiveData<ParseException>()

        fun makeRequest(customerRequest: CustomerRequest){
            val request = ParseObject("order")
            request.put("products_chosen", customerRequest.products)
            request.put("total_price", customerRequest.price!!)
            request.put("customer_id", ParseUser.getCurrentUser())
            request.put("order_date", "now date func")
            request.saveInBackground()
        }
        fun requestResult(): MutableLiveData<ParseException> {
            return requestLiveData
        }
    }
}
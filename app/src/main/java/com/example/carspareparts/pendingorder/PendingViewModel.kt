package com.example.carspareparts.pendingorder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class PendingViewModel : ViewModel() {
   private val ordersLiveData = MutableLiveData<List<ParseObject>>()
    fun getPendingOrders() {

        val userQuery = ParseQuery.getQuery<ParseObject>("customer")
        userQuery.whereEqualTo("user_id", ParseUser.getCurrentUser())

        val orderQuery =ParseQuery.getQuery<ParseObject>("order")

        userQuery.getFirstInBackground { customer, e ->
            if (e==null){
                orderQuery.whereEqualTo("customer_id",customer)
                orderQuery.whereEqualTo("delivered",false)
                orderQuery.findInBackground { orders, e ->
                    if (e==null)
                        ordersLiveData.postValue(orders)
                }
            }
        }

    }
    fun getListOrdersLiveData(): MutableLiveData<List<ParseObject>> {
        return ordersLiveData
    }
}

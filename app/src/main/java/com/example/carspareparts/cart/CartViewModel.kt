package com.example.carspareparts.cart

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.carspareparts.CustomCart
import com.parse.*
import java.util.*
import com.parse.ParseObject
import com.parse.GetCallback
import java.lang.reflect.Array
import com.parse.DeleteCallback




class CartViewModel : ViewModel() {

    private val cartItemsList = MutableLiveData<List<ParseObject>>()
    private val exception = MutableLiveData<ParseException>()
    fun getCartItems() {
        val cartQuery = ParseQuery.getQuery<ParseObject>("pinned_order")
        cartQuery.fromLocalDatastore()
        cartQuery.findInBackground { objects, e ->
            if (e == null) {
                cartItemsList.postValue(objects)
            }

        }
    }

     fun getCartLiveData(): MutableLiveData<List<ParseObject>> {
        return cartItemsList
    }

    fun makeOrder(list: List<ParseObject>?) {
        var total = 0
        var error=false
        val userQuery = ParseQuery.getQuery<ParseObject>("customer")
        userQuery.whereEqualTo("user_id", ParseUser.getCurrentUser())
        userQuery.getFirstInBackground { customer, e ->
            if (e == null) {

                val order = ParseObject("order")

//                val relation =  order.getRelation<ParseObject>("products_chosen")
                list?.forEach {
                    total += it.getInt("total_price")
//                    val po = ParseObject.createWithoutData("supplier_spare_part",it.getString("supplier_spare_part_id"))
//                    relation.add(po)
                }

                order.put("total_price", total)
                order.put("customer_id", customer)
                order.saveInBackground {
                    if (it != null){
                        exception.postValue(it)
                    }
                    else{
                        list?.forEach {
                            val orderSupplierSparePart = ParseObject("order_supplier_spare_part")
                            orderSupplierSparePart.put("order_id",order)
                            orderSupplierSparePart.put("supplier_spare_part_id"
                                ,ParseObject.createWithoutData("supplier_spare_part",it.getString("supplier_spare_part_id")!!)
                            )
                            orderSupplierSparePart.put("quantity",1)
                            orderSupplierSparePart.put("is_accepted",false)
                            orderSupplierSparePart.saveInBackground {
                                if(it!=null){
                                    error=true
                                }
                            }
                        }
                        if (error){
                            exception.postValue(it)
                        }
                        else{
                        ParseObject.unpinAllInBackground()
                        exception.postValue(null)
                        }
                    }
                }

            }
        }
    }

    fun getRequestException(): MutableLiveData<ParseException> {
        return exception
    }
}

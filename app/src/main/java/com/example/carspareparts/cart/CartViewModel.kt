package com.example.carspareparts.cart

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.parse.*
import java.util.*
import com.parse.ParseObject
import com.parse.GetCallback


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

        val userQuery = ParseQuery.getQuery<ParseObject>("customer")
        userQuery.whereEqualTo("user_id", ParseUser.getCurrentUser())
        userQuery.getFirstInBackground { `object`, e ->
            if (e == null) {

                val order = ParseObject("order")
                order.put("total_price", total)
                 val relation =  order.getRelation<ParseObject>("products_chosen")
                list?.map { it ->
                    total += it.getInt("total_price")
                    var parseObject = ParseObject("supplier_spare_part")
                    parseObject.put("price", it.getInt("total_price"))
                    parseObject.put("supplier_id",it.getString("supplier_id")!!)
                    parseObject.put("spare_part_id",it.getString("spare_part_id")!!)
                    parseObject.pinInBackground()

                    val cartQuery = ParseQuery.getQuery<ParseObject>("supplier_spare_part")
                    cartQuery.fromLocalDatastore()
                    cartQuery.whereEqualTo("product_name", it.getString("product_name"))
                    cartQuery.whereEqualTo("supplier_name", it.getString("supplier_name"))
                    cartQuery.findInBackground { objects, e ->
                        if (objects.size> 0) {
                            relation.add(objects.first())
                            order.getRelation<ParseObject>("products_chosen").add(objects.first())
                        }
                    }

                }

                order.put("order_date", Date().time)
                order.put("customer_id", `object`)
                order.saveInBackground {
                    if (it != null)
                        exception.postValue(it)
                }

            }
        }

    }

    fun getRequestException(): MutableLiveData<ParseException> {
        return exception
    }
}

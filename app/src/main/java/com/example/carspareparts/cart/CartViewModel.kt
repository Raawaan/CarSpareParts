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
                list?.map {
                    total += it.getInt("total_price")
                }
                val relation =  order.getRelation<ParseObject>("products_chosen")
                val parseQuerySupplier = ParseQuery.getQuery<ParseObject>("supplier_spare_part")
                parseQuerySupplier.fromLocalDatastore()
                parseQuerySupplier.findInBackground { objects, e ->
                    if (e==null)
                        objects.forEach {
                            val parseQuerySupplierServer =
                                ParseQuery.getQuery<ParseObject>("supplier_spare_part")
                                parseQuerySupplierServer.whereEqualTo("price",it.getInt("price"))
                                parseQuerySupplierServer.whereEqualTo("supplier_id",it.getString("supplier_id"))
                                parseQuerySupplierServer.whereEqualTo("spare_part_id",it.getString("spare_part_id"))
                                parseQuerySupplierServer.getFirstInBackground { `object`, e ->
                                    if(e==null)
                                    relation.add(`object`)
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

package com.example.carspareparts.sparepartdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.SparePartDetails
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseObject
import java.util.*


class SparePartDetailsViewModel : ViewModel() {
    private val pinException = MutableLiveData<ParseException>()
    private val stringException = MutableLiveData<String>()
    fun addItemToCart(sparePart: SparePartDetails?) {
        val cartItem = ParseObject("pinned_order")
        cartItem.put("product_name", sparePart?.name!!)
        cartItem.put("supplier_name", sparePart.supplierName!!)
        cartItem.put("total_price", sparePart.price!!)
        cartItem.put("supplier_id", sparePart.supplierId!!)
        cartItem.put("spare_part_id", sparePart.sparePartId!!)
        cartItem.put("supplier_spare_part_id",sparePart.objectId!!)

        val cartQuery = ParseQuery.getQuery<ParseObject>("pinned_order")
        cartQuery.fromLocalDatastore()
        cartQuery.whereEqualTo("product_name", sparePart.name)
        cartQuery.whereEqualTo("supplier_name", sparePart.supplierName)
        cartQuery.findInBackground { objects, e ->
            if (objects.size == 0) {
                cartItem.pinInBackground {
                    pinException.postValue(it)
                }

            } else{
                stringException.postValue("already added item to cart")
                pinException.postValue(e)

            }
        }
    }


    fun requestResult(): MutableLiveData<ParseException> {
        return pinException
    }
    fun getStringException(): MutableLiveData<String> {
        return stringException
    }
}

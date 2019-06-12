package com.example.carspareparts.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.SparePartDetails
import com.parse.ParseObject
import com.parse.ParseQuery

class OrderDetailsViewModel : ViewModel() {
    private val sparePartDetails = mutableListOf<SparePartDetails>()
    private val sparePartDetailsLiveData = MutableLiveData<List<SparePartDetails>>()

    fun getRelationsProductInfo(order: ParseObject?) {
        sparePartDetails.clear()
        val productsChosen= order?.getRelation<ParseObject>("products_chosen")?.query

        productsChosen?.include("spare_part_id")
        productsChosen?.include("supplier_id")
        productsChosen?.include("spare_part_type_id")
        productsChosen?.findInBackground { objects, e ->
            objects.forEach {
                val sparePartClass = it.getParseObject("spare_part_id")
                val sparePartSupplierClass = it.getParseObject("supplier_id")
                val parseUser = sparePartSupplierClass?.getParseUser("user_id")?.fetchIfNeeded()
                val sparePartTypeClass =
                    sparePartClass?.getParseObject("spare_part_type_id")?.fetchIfNeeded<ParseObject>()
                    sparePartDetails.add(
                        SparePartDetails(
                            it.objectId,
                            sparePartClass?.getString("name"),
                            sparePartTypeClass?.getString("type"),
                            it.getInt("price"),
                            sparePartClass,
                            sparePartSupplierClass,
                            sparePartClass?.getString("description"),
                            parseUser?.getString("username"),
                            sparePartClass?.getString("product_image"),
                            sparePartSupplierClass?.getString("supplier_logo")
                        )
                    )
            }
            sparePartDetailsLiveData.postValue(sparePartDetails)
        }

    }
    fun getSparePartDetails(): MutableLiveData<List<SparePartDetails>> {
        return sparePartDetailsLiveData
    }
}

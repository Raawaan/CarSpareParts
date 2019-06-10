package com.example.carspareparts.sparepartproducts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carspareparts.SparePartDetails
import com.parse.ParseObject
import com.parse.ParseQuery

/**
 * Created by rawan on 02/06/19 .
 */
class SparePartProductViewModel : ViewModel() {
    private val sparePartDetailsLiveData = MutableLiveData<List<SparePartDetails>>()
    private val sparePartDetails = mutableListOf<SparePartDetails>()
    fun getProductsByType(objectId: String) {
        sparePartDetails.clear()
        val parseSparePartQuery = ParseQuery<ParseObject>("supplier_spare_part")
        parseSparePartQuery.include("spare_part_id")
        parseSparePartQuery.include("supplier_id")
        parseSparePartQuery.include("spare_part_type_id")
        parseSparePartQuery.findInBackground { objects, e ->
            objects.forEach {
                val sparePartClass = it.getParseObject("spare_part_id")
                val sparePartSupplierClass = it.getParseObject("supplier_id")
                val parseUser = sparePartSupplierClass?.getParseUser("user_id")?.fetchIfNeeded()
                val sparePartTypeClass =
                    sparePartClass?.getParseObject("spare_part_type_id")?.fetchIfNeeded<ParseObject>()
                if (sparePartTypeClass?.objectId == objectId) {
                    sparePartDetails.add(
                        SparePartDetails(
                            it.objectId,
                            sparePartClass.getString("name"),
                            sparePartTypeClass.getString("type"),
                            it.getInt("price"),
                            sparePartClass,
                            sparePartSupplierClass,
                            sparePartClass.getString("description"),
                            parseUser?.getString("username"),
                            sparePartClass.getString("product_image")
                        )
                    )
                }
            }
            sparePartDetailsLiveData.postValue(sparePartDetails)

        }

    }

    fun getSparePartDetails(): MutableLiveData<List<SparePartDetails>> {
        return sparePartDetailsLiveData
    }
}
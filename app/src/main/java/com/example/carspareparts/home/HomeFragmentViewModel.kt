package com.example.carspareparts.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.SparePartDetails
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.*
import kotlin.collections.ArrayList

class HomeFragmentViewModel : ViewModel() {

    companion object {

        fun getInstance(homeFragment: HomeFragment) =
            ViewModelProviders.of(homeFragment).get(HomeFragmentViewModel::class.java)
    }

    private var categoryItems = arrayListOf<SparePartDetails>()
        set(value) {
            field = value
            updateItemsLiveData(value)
        }

    val viewPagerImages = MutableLiveData<List<String>>()
    val categories = MutableLiveData<List<ParseObject>>()
    val exclusiveItem = MutableLiveData<SparePartDetails>()
    val otherItems = MutableLiveData<List<SparePartDetails>>()
    val isAllSet = MutableLiveData<Boolean>()

    fun loadHomeData() {
        loadImages()
        loadCategories()
    }

    private fun updateItemsLiveData(items: ArrayList<SparePartDetails>) {
        if (items.isNotEmpty()) {
            exclusiveItem.value = items[0]
            if (items.size > 1) {
                otherItems.value = items.subList(1, items.size)
            }
            isAllSet.value = true
        }
    }

    private fun loadImages() {
        viewPagerImages.value = arrayListOf(
            "https://images.yaoota.com/h812rLfmhckE0jIu1QKGWKtF2aI=/trim/yaootaweb-production/media/crawledproductimages/e4e8528069a1d82a3f7c032642680049567659f2.jpg",
            "https://eg.jumia.is/23dg4rrmdG-_QGd5JrNrsRiOvvE=/fit-in/220x220/filters:fill(white)/product/35/452421/1.jpg?8763",
            "https://images-na.ssl-images-amazon.com/images/I/81bv8%2BVcJhL._SY606_.jpg",
            "https://ae01.alicdn.com/kf/HTB19t5IKVXXXXX7aXXXq6xXFXXX2/car-Seat-Supports-Cushion-waist-pad-Comfortable-Mesh-Chair-Relief-Lumbar-Back-Pain-Support-Car-Cushion.jpg_640x640.jpg",
            "https://www.dailysale.com/media/catalog/product/cache/1/image/600x600/9df78eab33525d08d6e5fb8d27136e95/3/4/343831-3-lg.1555376731.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/6158fVwKSlL._SX425_.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/6109dZ4zB8L._SY355_.jpg",
            "https://assets.weathertech.com/assets/1/19/713x535/444861-01-444863.jpg",
            "https://orangeauto.ae/wp-content/uploads/window-tinting.jpg",
            "http://www.tototiresupply.ph/wp-content/uploads/2018/08/EAGLE-EFFICIENTGRIP.png"
        )
    }

    private fun loadCategories() {
        val sparePartTypeQuery = ParseQuery<ParseObject>("supplier_spare_part")
        sparePartTypeQuery.include("spare_part_id")
        sparePartTypeQuery.include("spare_part_type_id")
        val hashSet = hashSetOf<ParseObject>()
        sparePartTypeQuery.findInBackground { objects, error ->
            if (error == null) {
                objects.forEach { categoryObject ->
                    val category = categoryObject
                        .getParseObject("spare_part_id")
                        ?.getParseObject("spare_part_type_id")
                        ?.fetchIfNeeded<ParseObject>()
                    category?.run(hashSet::add)
                }
                val categories = hashSet.toList()
                if (categories.isNotEmpty()) {
                    val randomCategory = categories[Random().nextInt(categories.size)]
                    getProductsByType(randomCategory.objectId)
                }
                this.categories.value = categories
            }
        }
    }


    private fun getProductsByType(objectId: String) {
        val items = arrayListOf<SparePartDetails>()
        val parseSparePartQuery = ParseQuery<ParseObject>("supplier_spare_part")
        parseSparePartQuery.include("spare_part_id")
        parseSparePartQuery.include("supplier_id")
        parseSparePartQuery.include("spare_part_type_id")
        parseSparePartQuery.findInBackground { objects, _ ->
            objects.forEach {
                val sparePartClass = it.getParseObject("spare_part_id")
                val sparePartSupplierClass = it.getParseObject("supplier_id")
                val parseUser = sparePartSupplierClass?.getParseUser("user_id")?.fetchIfNeeded()
                val sparePartTypeClass =
                    sparePartClass?.getParseObject("spare_part_type_id")?.fetchIfNeeded<ParseObject>()
                if (sparePartTypeClass?.objectId == objectId) {
                    items.add(
                        SparePartDetails(
                            it.objectId,
                            sparePartClass.getString("name"),
                            sparePartTypeClass.getString("type"),
                            it.getInt("price"),
                            sparePartClass,
                            sparePartSupplierClass,
                            sparePartClass.getString("description"),
                            parseUser?.getString("username"),
                            sparePartClass.getString("product_image"),
                            sparePartSupplierClass?.getString("supplier_logo"),
                            false,
                            it.getInt("quantity")
                        )
                    )
                }
            }
            categoryItems = items
        }

    }
}

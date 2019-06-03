package com.example.carspareparts.sparepartproducts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carspareparts.R

class SparePartProductsActivity : AppCompatActivity() {
        lateinit var sparePartProductViewModel: SparePartProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spare_part_products)

        sparePartProductViewModel= SparePartProductViewModel()
        val objectClickedId = intent?.extras!!["objectId"] as String
        val objectClickedType = intent?.extras!!["typeName"]
        sparePartProductViewModel.getProductsByType(objectClickedId)
    }
}

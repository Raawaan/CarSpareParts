package com.example.carspareparts.sparepartdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carspareparts.R
import com.example.carspareparts.sparepartproducts.SparePartDetails

class SparePartDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spare_part_details)
        val sparePartDetails = intent.extras["sparePartDetails"] as SparePartDetails
    }
}

package com.example.carspareparts.sparepartdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.carspareparts.R
import com.example.carspareparts.sparepartproducts.SparePartDetails
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_spare_part_details.view.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.spare_part_details_content.view.*


class SparePartDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.carspareparts.R.layout.activity_spare_part_details)
        val sparePartDetails = intent.extras["sparePartDetails"] as SparePartDetails

        val productTitle = findViewById<TextView>(com.example.carspareparts.R.id.productNameTextView)
        productTitle.text = sparePartDetails.name

        val productDesc = findViewById<TextView>(com.example.carspareparts.R.id.descriptionTextView)
        productTitle.text = sparePartDetails.description

        val productSupplier = findViewById<TextView>(com.example.carspareparts.R.id.supplierTextView)
        productSupplier.text = sparePartDetails.supplierName

         val productImageView =  findViewById<ImageView>(com.example.carspareparts.R.id.productImageView)
        Picasso.get().load(sparePartDetails.image).into(productImageView)
    }
}

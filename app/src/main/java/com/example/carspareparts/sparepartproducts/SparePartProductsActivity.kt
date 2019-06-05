package com.example.carspareparts.sparepartproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.sparepartdetails.SparePartDetailsActivity
import kotlinx.android.synthetic.main.activity_spare_part_products.*



class SparePartProductsActivity : AppCompatActivity() {
    private lateinit var sparePartProductViewModel: SparePartProductViewModel
    private lateinit var sparePartProductAdapter:SparePartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spare_part_products)

        sparePartProductToolbar.title=intent?.extras!!["typeName"] as String
        setSupportActionBar(sparePartProductToolbar)
        val objectClickedId = intent?.extras!!["objectId"] as String
        sparePartProductViewModel= ViewModelProviders.of(this)
            .get(SparePartProductViewModel::class.java)

        listOfSpareDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        sparePartProductViewModel.getProductsByType(objectClickedId)
        sparePartProductViewModel.getSparePartDetails().observe(this, Observer {
            sparePartProductAdapter= SparePartProductAdapter(it) {
               val intent = Intent(this,SparePartDetailsActivity::class.java)
                intent.putExtra("sparePartDetails",it)
                startActivity(intent)
                true
            }
            listOfSpareDetailsRecyclerView.adapter = sparePartProductAdapter

        })


    }

}
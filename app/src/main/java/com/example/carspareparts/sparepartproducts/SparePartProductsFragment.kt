package com.example.carspareparts.sparepartproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.sparepartdetails.SparePartDetailsActivity
import kotlinx.android.synthetic.main.activity_spare_part_products.*



class SparePartProductsFragment : Fragment() {
    private lateinit var sparePartProductViewModel: SparePartProductViewModel
    private lateinit var sparePartProductAdapter:SparePartProductAdapter
    companion object {
        fun newInstance() = SparePartProductsFragment()


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_spare_part_products, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState!!["objectId"]
        val objectClickedId = ?.extras!![""] as String
        sparePartProductViewModel= ViewModelProviders.of(this)
            .get(SparePartProductViewModel::class.java)

//        listOfSpareDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
//        sparePartProductViewModel.getProductsByType(objectClickedId)
        sparePartProductViewModel.getSparePartDetails().observe(this, Observer {
            sparePartProductAdapter= SparePartProductAdapter(it) {
//                val intent = Intent(this,SparePartDetailsActivity::class.java)
//                intent.putExtra("sparePartDetails",it)
//                startActivity(intent)
                true
            }
//            listOfSpareDetailsRecyclerView.adapter = sparePartProductAdapter

        })


    }

}

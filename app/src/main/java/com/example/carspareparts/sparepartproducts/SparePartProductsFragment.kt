package com.example.carspareparts.sparepartproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.sparepartdetails.SparePartDetailsActivity
import kotlinx.android.synthetic.main.activity_spare_part_products.*
import kotlinx.android.synthetic.main.app_bar_home.*


class SparePartProductsFragment : Fragment() {
    private lateinit var sparePartProductViewModel: SparePartProductViewModel
    private lateinit var sparePartProductAdapter:SparePartProductAdapter
    private lateinit var objectClickedId :String
    companion object {
        fun newInstance() = SparePartProductsFragment()


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_spare_part_products, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sparePartProductViewModel= ViewModelProviders.of(this)
            .get(SparePartProductViewModel::class.java)
        activity?.toolbar?.title= arguments?.getString("typeName").toString()
        objectClickedId= arguments?.getString("objectId").toString()
        listOfSpareDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        sparePartProductViewModel.getProductsByType(objectClickedId)
        sparePartProductViewModel.getSparePartDetails().observe(this, Observer {
            sparePartProductAdapter= SparePartProductAdapter(it) {
//                val intent = Intent(this,SparePartDetailsActivity::class.java)
//                intent.putExtra("sparePartDetails",it)
//                startActivity(intent)
                true
            }
            listOfSpareDetailsRecyclerView.adapter = sparePartProductAdapter

        })


    }

}

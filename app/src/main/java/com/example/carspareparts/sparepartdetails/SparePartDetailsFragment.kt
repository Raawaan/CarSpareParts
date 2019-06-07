package com.example.carspareparts.sparepartdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.CustomerRequest
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_spare_part_details.*
import kotlinx.android.synthetic.main.app_bar_home.*

class SparePartDetailsFragment : Fragment() {
lateinit var sparePartDetailsViewModel: SparePartDetailsViewModel
    companion object {
        fun newInstance() = SparePartDetailsFragment()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_spare_part_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sparePartDetails = arguments?.getParcelable<SparePartDetails>("sparePartDetails")
        sparePartDetails?.supplierName
        activity?.toolbar?.title="${sparePartDetails?.name} Details"
        productNameTextView.text = sparePartDetails?.name

        descriptionTextView.text = sparePartDetails?.description

        supplierTextView.text = sparePartDetails?.supplierName

        Picasso.get().load(sparePartDetails?.image).into(productImageView)
        sparePartDetailsViewModel= ViewModelProviders.of(this).get(SparePartDetailsViewModel::class.java)
        addToCartBtn.setOnClickListener {
            sparePartDetailsViewModel.addItemToCart(sparePartDetails)

        }
        sparePartDetailsViewModel.requestResult().observe(this, Observer {
            if(it==null){
                activity?.cartView?.getAllInCart()
                Toast.makeText(context,"3ash",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()

        })
        sparePartDetailsViewModel.getAllInCart()
        sparePartDetailsViewModel.getLiveDataCart().observe(this, Observer {
            Toast.makeText(context,it.size.toString(),Toast.LENGTH_LONG).show()

            it.forEach {
                val cartProduct = it.getString("product_name")
                Toast.makeText(context,cartProduct,Toast.LENGTH_LONG).show()
            }
        })
        sparePartDetailsViewModel.getStringException().observe(this, Observer {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()

        })
    }
}

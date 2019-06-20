package com.example.carspareparts.sparepartdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.ConnectionLiveData
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.main.BaseFragmentInteractionListener
import com.example.carspareparts.main.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_spare_part_details.*
import kotlinx.android.synthetic.main.app_bar_home.*

const val ARG_PRODUCT = "com.example.carspareparts.sparepartdetails.SparePartDetailsFragment.ARG_PRODUCT"

class SparePartDetailsFragment : Fragment() {
lateinit var sparePartDetailsViewModel: MainViewModel

    private var listener: OnFragmentInteractionListener? = null

    companion object {

        fun newInstance(product: SparePartDetails) = SparePartDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PRODUCT, product)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_spare_part_details, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var quantity=1
        quantityTextView.text=quantity.toString()
        val sparePartDetails = arguments?.getParcelable<SparePartDetails>(ARG_PRODUCT)
        sparePartDetails?.supplierName
        activity?.toolbar?.title="${sparePartDetails?.name} Details"
        productNameTextView.text = sparePartDetails?.name

        descriptionTextView.text = sparePartDetails?.description

        supplierTextView.text = sparePartDetails?.supplierName
        detailsPrice.text=sparePartDetails?.price.toString().plus(" LE")
        Picasso.get().load(sparePartDetails?.image).into(productImageView)
        ConnectionLiveData.observe(this, Observer {
            addToCartBtn.isClickable = it!=null&&it.isConnected

        })

        sparePartDetailsViewModel= ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        addToCartBtn.setOnClickListener {
            addToCartBtn.isClickable=false
            sparePartDetails?.run {
                listener?.onAddToCartClick(this, quantity)
            }
        }
              plusBtn.setOnClickListener {
        if (quantity<4){
            quantity++
            quantityTextView.text=quantity.toString()
            }
            else
            Toast.makeText(context,"quantity can't be more than 4 items",Toast.LENGTH_SHORT).show()
        }
        minusBtn.setOnClickListener {
            if(quantity>1){
                quantity--
                quantityTextView.text=quantity.toString()
            }
            else
            Toast.makeText(context,"quantity can't be less than one item",Toast.LENGTH_SHORT).show()
        }
        sparePartDetailsViewModel.requestResult().observe(this, Observer {
            if(it==null){
                activity?.cartView?.getAllInCart()
            }
            else
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()

        })
        sparePartDetailsViewModel.getStringException().observe(this, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()

        })
    }

    interface OnFragmentInteractionListener : BaseFragmentInteractionListener
}

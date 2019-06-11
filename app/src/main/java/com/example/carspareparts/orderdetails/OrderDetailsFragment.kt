package com.example.carspareparts.orderdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.carspareparts.R
import com.parse.ParseObject
import kotlinx.android.synthetic.main.order_details_fragment.*

class OrderDetailsFragment : Fragment() {
    lateinit var orderDetailsAdapter: OrderDetailsAdapter
    companion object {
        fun newInstance() = OrderDetailsFragment()
    }

    private lateinit var viewModel: OrderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel::class.java)
        viewModel.getRelationsProductInfo(arguments?.getParcelable<ParseObject>("selectedOrder"))
        ordersDetails.layoutManager= LinearLayoutManager(context)
        viewModel.getSparePartDetails().observe(this, Observer {
            if(!it.isNullOrEmpty()){
            orderDetailsAdapter=OrderDetailsAdapter(it)
            ordersDetails.adapter = orderDetailsAdapter
            }
        })
    }

}

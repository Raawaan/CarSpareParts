package com.example.carspareparts.orderhistory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.carspareparts.R

import kotlinx.android.synthetic.main.order_history_fragment.*

class OrderHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = OrderHistoryFragment()
    }

    private lateinit var viewModel: OrderHistoryViewModel
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderHistoryViewModel::class.java)
        viewModel.getOrderHistory()
        orderHistoryRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getListOrdersLiveData().observe(this, Observer {
            if (it!=null){
                orderHistoryAdapter=OrderHistoryAdapter(it,{true})
                orderHistoryRecyclerView.adapter=orderHistoryAdapter
            }

        })
    }

}

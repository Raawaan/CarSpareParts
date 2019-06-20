package com.example.carspareparts.orderdetails

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.ConnectionLiveData

import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.main.BaseFragmentInteractionListener
import com.parse.ParseObject
import kotlinx.android.synthetic.main.order_details_fragment.*

class OrderDetailsFragment : Fragment(), OrderDetailsAdapter.OnItemClickListener {

    private var listener: OnFragmentInteractionListener? = null

    override fun onProductClick(product: SparePartDetails) =
        listener?.onProductClick(product) ?: Unit

    override fun onAddToCartClick(product: SparePartDetails) =
        listener?.onAddToCartClick(product) ?: Unit

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
        viewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel::class.java)
        ConnectionLiveData.observe(this, Observer {
            if (it!=null&&it.isConnected){
                viewModel.getRelationsProductInfo(arguments?.getParcelable("selectedOrder"))
            }
        })
        ordersDetails.layoutManager= LinearLayoutManager(context)
        viewModel.getSparePartDetails().observe(this, Observer {
            if(!it.isNullOrEmpty()){
                ordersDetailsProgressBar.visibility=View.GONE
            orderDetailsAdapter=OrderDetailsAdapter(this, it)
            ordersDetails.adapter = orderDetailsAdapter
            }
        })
    }

    interface OnFragmentInteractionListener : BaseFragmentInteractionListener
}

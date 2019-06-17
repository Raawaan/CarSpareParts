package com.example.carspareparts.order

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.ConnectionLiveData

import com.example.carspareparts.R
import com.example.carspareparts.orderdetails.OrderDetailsFragment
import kotlinx.android.synthetic.main.pending_fragment.*

class OrdersFragment : Fragment() {

    companion object {
        fun newInstance() = OrdersFragment()
    }
    private val fragment:Fragment by lazy {
        OrderDetailsFragment.newInstance()
    }

    private lateinit var viewModel: OrdersViewModel
    private lateinit var ordersAdapter: OrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pending_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        ConnectionLiveData.observe(this, Observer {
            if (it!=null&&it.isConnected){
                viewModel.getPendingOrders()
            }
        })
        viewModel.getPendingOrders()
        pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getListOrdersLiveData().observe(this, Observer {
            if (it?.size==0) {
                noOrders.visibility=View.VISIBLE
                ordersListProgressBar.visibility=View.GONE
            }
            else if (it!=null) {
                ordersListProgressBar.visibility=View.GONE
                ordersAdapter=OrdersAdapter(it){
                    val bundle = Bundle()
                    bundle.putParcelable("selectedOrder", it)
                    fragment.arguments=bundle
                    val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                    fragmentTransaction?.add(R.id.fragmentPlaceholder, fragment)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                    true
                }
                pendingOrdersRecyclerView.adapter=ordersAdapter
            }

        })
    }
}

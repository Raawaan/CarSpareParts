package com.example.carspareparts.sparepartproducts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.ConnectionLiveData
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.main.BaseFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_spare_part_products.*
import kotlinx.android.synthetic.main.app_bar_home.*

private const val ARG_CATEGORY_ID = "com.example.carspareparts.sparepartproducts.SparePartProductsFragment.ARG_CATEGORY_ID"
private const val ARG_CATEGORY_NAME = "com.example.carspareparts.sparepartproducts.SparePartProductsFragment.ARG_CATEGORY_NAME"

class SparePartProductsFragment : Fragment(), SparePartProductAdapter.OnItemClickListener {

    override fun onAddToCartClick(product: SparePartDetails) =
        listener?.onAddToCartClick(product) ?: Unit

    override fun onProductClick(product: SparePartDetails) =
        listener?.onProductClick(product) ?: Unit

    private lateinit var sparePartProductViewModel: SparePartProductViewModel
    private lateinit var sparePartProductAdapter:SparePartProductAdapter
    private lateinit var objectClickedId :String

    private var listener: OnFragmentInteractionListener? = null

    companion object {

        fun newInstance(categoryId: String, categoryName: String) = SparePartProductsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY_ID, categoryId)
                putString(ARG_CATEGORY_NAME, categoryName)
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_spare_part_products, container, false)
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
        sparePartProductViewModel= ViewModelProviders.of(this)
            .get(SparePartProductViewModel::class.java)
        activity?.toolbar?.title= arguments?.getString(ARG_CATEGORY_NAME).toString()
        objectClickedId= arguments?.getString(ARG_CATEGORY_ID).toString()
        listOfSpareDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        ConnectionLiveData.observe(this, Observer {
            if (it!=null&&it.isConnected){
                sparePartProductViewModel.getProductsByType(objectClickedId)
            }
        })

        sparePartProductViewModel.getSparePartDetails().observe(this, Observer {
            progressBar.visibility=View.GONE
            sparePartProductAdapter= SparePartProductAdapter(this, it) { product ->
                listener?.onProductClick(product)
                true
            }
            listOfSpareDetailsRecyclerView.adapter = sparePartProductAdapter
            listOfSpareDetailsRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        })
    }

    interface OnFragmentInteractionListener : BaseFragmentInteractionListener
}

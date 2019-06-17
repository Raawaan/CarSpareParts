package com.example.carspareparts.sparepartproducts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.main.MainActivity
import com.example.carspareparts.sparepartdetails.SparePartDetailsFragment
import kotlinx.android.synthetic.main.activity_spare_part_products.*
import kotlinx.android.synthetic.main.app_bar_home.*


class SparePartProductsFragment : Fragment() {
    private lateinit var sparePartProductViewModel: SparePartProductViewModel
    private lateinit var sparePartProductAdapter:SparePartProductAdapter
    private lateinit var objectClickedId :String
    private val fragment:Fragment by lazy {
        SparePartDetailsFragment.newInstance()
    }

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
            progressBar.visibility=View.GONE
            sparePartProductAdapter= SparePartProductAdapter(it) {
                val bundle = Bundle()
                bundle.putParcelable("sparePartDetails", it)
                fragment.arguments=bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.add(R.id.fragmentPlaceholder, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
                true
            }
            listOfSpareDetailsRecyclerView.adapter = sparePartProductAdapter
            listOfSpareDetailsRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        })



    }


}

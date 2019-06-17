package com.example.carspareparts.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.sparepartproducts.SparePartProductsFragment
import com.parse.ParseException
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.categories_fragment.*


class CategoriesFragment : Fragment() {

    private lateinit var sparePartTypeAdapter: SparePartTypeAdapter
    private lateinit var categoriesFragmentViewModel: CategoriesFragmentViewModel
    private val fragment:Fragment by lazy {
        SparePartProductsFragment.newInstance()
    }
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.carspareparts.R.layout.categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoriesFragmentViewModel= ViewModelProviders.of(this).get(CategoriesFragmentViewModel::class.java)

        categoriesFragmentViewModel.getSparePartTypeList()
        listOfSparePartTypRecyclerView.layoutManager = LinearLayoutManager(activity)
        categoriesFragmentViewModel.getSparePartTypeLiveData().observe(this, Observer {
            if(it !is ParseException){
                homeProgressBar.visibility=View.GONE
            sparePartTypeAdapter = SparePartTypeAdapter(it) {
                val bundle = Bundle()
                bundle.putString("objectId", it.first)
                bundle.putString("typeName", it.second)
                fragment.arguments=bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.add(R.id.fragmentPlaceholder, fragment)
                fragmentTransaction?.addToBackStack("home")
                fragmentTransaction?.commit()
                true
            }
                listOfSparePartTypRecyclerView.adapter = sparePartTypeAdapter
            }
//            else{
//                Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
//            }
        })



    }

    override fun onResume() {
        super.onResume()
        activity?.toolbar ?.title= "Available Categories"

    }

}

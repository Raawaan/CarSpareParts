package com.example.carspareparts.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.main.MainActivity
import com.example.carspareparts.sparepartproducts.SparePartProductsFragment
import com.parse.ParseException
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    private lateinit var sparePartTypeAdapter: SparePartTypeAdapter
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    companion object {
        fun newInstance() = HomeFragment()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeFragmentViewModel= ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        homeFragmentViewModel.getSparePartTypeList()
        listOfSparePartTypeRecyclerView.layoutManager = LinearLayoutManager(activity)
        homeFragmentViewModel.getSparePartTypeLiveData().observe(this, Observer {
            if(it !is ParseException){
            sparePartTypeAdapter = SparePartTypeAdapter(it) {
                (activity as MainActivity).replaceFragmentListener(SparePartProductsFragment.newInstance(),it)
//                intent.putExtra("objectId",it.first)
//                intent.putExtra("typeName",it.second)
                true
            }
                listOfSparePartTypeRecyclerView.adapter = sparePartTypeAdapter
            }
//            else{
//                Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
//            }
        })

    }

}

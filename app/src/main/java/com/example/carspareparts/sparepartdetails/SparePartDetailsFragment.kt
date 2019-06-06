package com.example.carspareparts.sparepartdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carspareparts.R
import com.example.carspareparts.sparepartproducts.SparePartDetails
import kotlinx.android.synthetic.main.app_bar_home.*

class SparePartDetailsFragment : Fragment() {

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

    }
}

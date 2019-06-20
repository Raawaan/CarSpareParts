package com.example.carspareparts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*
import android.content.Intent
import android.net.Uri


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contactUs.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"

            intent.putExtra(Intent.EXTRA_EMAIL, arrayListOf("sbarly@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Customer Feedback")

            startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }
}

package com.example.carspareparts.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.parse.ParseException
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.cart_details.*


class CartActivity : AppCompatActivity() {

    lateinit var cartAdapter: CartAdapter
    lateinit var cartActivity: CartActivity
    lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_details)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartToolbar?.title= "Cart Details"
        setSupportActionBar(cartToolbar)

        cartViewModel.getCartItems()

        cartDetails.layoutManager = LinearLayoutManager(this)
        cartViewModel.getCartLiveData().observe(this, Observer { list  ->
            if(list !is ParseException){
                if(!list.isNullOrEmpty()){
                    orderBtn.setOnClickListener {
                        cartViewModel.makeOrder(list)
                    }
                 }
                cartAdapter = CartAdapter(list) {
                    true
                }
                cartDetails.adapter = cartAdapter

            }
             else{
                Toast.makeText(this,list.message,Toast.LENGTH_LONG).show()
            }
        })
        cartViewModel.getRequestException().observe(this, Observer {
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        })
    }

}

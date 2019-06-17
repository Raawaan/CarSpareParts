package com.example.carspareparts.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.parse.ParseException
import com.parse.ParseObject
import kotlinx.android.synthetic.main.cart_details.*
import kotlinx.android.synthetic.main.enter_address_dialog.*
import kotlinx.android.synthetic.main.enter_address_dialog.view.*


class CartActivity : AppCompatActivity() {

    lateinit var cartAdapter: CartAdapter
    lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_details)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartToolbar?.title = "Cart Details"
        setSupportActionBar(cartToolbar)

        cartViewModel.getCartItems()

        cartDetails.layoutManager = LinearLayoutManager(this)
        cartViewModel.getCartLiveData().observe(this, Observer { list ->
            if (list !is ParseException) {
                if (!list.isNullOrEmpty()) {
                    orderBtn.isClickable = true
                    cartNoItems.visibility = View.GONE
                    cartDetails.visibility = View.VISIBLE
                    orderBtn.setOnClickListener {
                        orderBtn.isClickable = false
                        createDialog(list)

                    }
                }
                cartAdapter = CartAdapter(list as MutableList<ParseObject>) {
                    cartViewModel.removeItemFromCart(it)
                    true
                }
                cartDetails.adapter = cartAdapter

            } else {
                Toast.makeText(this, list.message, Toast.LENGTH_SHORT).show()
            }
        })
        cartViewModel.getRequestException().observe(this, Observer {
            if (it != null)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            else{
                finish()
            }
        })
        cartViewModel.notifyAdapterToChange().observe(this, Observer {
            cartAdapter.remove(it)
            cartAdapter.observeListIsEmpty().observe(this, Observer {
                orderBtn.isClickable = false
                cartDetails.visibility = View.GONE
                cartNoItems.visibility = View.VISIBLE

            })

        })

    }

    private fun createDialog(list:List<ParseObject>) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.enter_address_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Enter Delivery Address")
        val mAlertDialog = mBuilder.show()
        mDialogView.dialogConfirmBtn.setOnClickListener {
            if (mDialogView.buildingNoTv.text.trim().isEmpty()||
                mDialogView.streetTv.text.trim().isEmpty()||
                mDialogView.cityTv.text.trim().isEmpty()||
                mDialogView.countryTv.text.trim().isEmpty()
            ) {
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()

            } else {
                cartViewModel.makeOrder(list,mDialogView.streetTv.text.toString()
                    .plus(" "+mDialogView.streetTv.text.toString())
                    .plus(" "+mDialogView.cityTv.text.toString())
                    .plus(" "+mDialogView.countryTv.text.toString()))
                mAlertDialog.dismiss()
            }
        }
        mDialogView.dialogCancelBtn.setOnClickListener {
            orderBtn.isClickable = true
            mAlertDialog.dismiss()
        }
    }

}

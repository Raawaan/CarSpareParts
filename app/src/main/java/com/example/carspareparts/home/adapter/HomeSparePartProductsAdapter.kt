package com.example.carspareparts.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.setImageUrl
import kotlinx.android.synthetic.main.horizontal_list_item_home_item.view.*

class HomeSparePartProductsAdapter(private val itemClickListener: OnItemClickListener?) :
        RecyclerView.Adapter<HomeSparePartProductsAdapter.HomeSpareProductsViewHolder>() {

    var sparsePartProducts: List<SparePartDetails>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeSpareProductsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.horizontal_list_item_home_item, parent, false)
    )

    override fun getItemCount() = sparsePartProducts?.size ?: 0

    override fun onBindViewHolder(holder: HomeSpareProductsViewHolder, position: Int) = holder.setContent(this)

    class HomeSpareProductsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun setContent(adapter: HomeSparePartProductsAdapter) = view.run view@ {
            val sparsePartProducts = adapter.sparsePartProducts?.get(adapterPosition)
            sparsePartProducts?.run product@ {
                itemImageView.setImageUrl(image)
                itemNameTextView.text = name
                itemPriceTextView.text = "$price LE"
                this@view.setOnClickListener {
                    adapter.itemClickListener?.onProductClick(this@product)
                }
                buyButton.setOnClickListener {
                    adapter.itemClickListener?.onAddToCartClick(this@product)
                }
            }
            Unit
        }
    }

    interface OnItemClickListener {

        fun onProductClick(product: SparePartDetails)

        fun onAddToCartClick(product: SparePartDetails)
    }
}
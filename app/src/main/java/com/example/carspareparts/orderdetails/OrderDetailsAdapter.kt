package com.example.carspareparts.orderdetails

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_shopping_item.view.*

class OrderDetailsAdapter(private val itemClickListener: OnItemClickListener?,
                          private val products: List<SparePartDetails>) :
    RecyclerView.Adapter<OrderDetailsAdapter.SparePartProductHolderView>() {
    open class SparePartProductHolderView(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: SparePartProductHolderView, position: Int) {
        val product = products[position]
        holder.itemView.nameTextView.text = product.name
        holder.itemView.priceTextView.text = product.price.toString()
        holder.itemView.statusTextView.visibility = View.VISIBLE
        if (product.delivered) {
            holder.itemView.statusTextView.text = "Delivered"
            holder.itemView.statusTextView.setTextColor(Color.GREEN)
        } else {
            holder.itemView.statusTextView.text = "Not Delivered"
            holder.itemView.statusTextView.setTextColor(Color.RED)
        }
        holder.itemView.counterTextView.text = product.quantity.toString()

        Picasso.get().load(product.image).into(holder.itemView.itemImageView)
        holder.itemView.buyButton.setOnClickListener {
            itemClickListener?.onAddToCartClick(  product)
        }
        holder.itemView.detailsButton.setOnClickListener {
            itemClickListener?.onProductClick(product)
        }
//        Picasso.get().load(product.supplierLogo).into(holder.itemView.supplier_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartProductHolderView {
        return SparePartProductHolderView(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_shopping_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = products.size

    interface OnItemClickListener {

        fun onProductClick(product: SparePartDetails)

        fun onAddToCartClick(product: SparePartDetails)
    }
}
package com.example.carspareparts.orderdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.spare_part_details_content.view.*

class OrderDetailsAdapter(private val products: List<SparePartDetails>): RecyclerView.Adapter<OrderDetailsAdapter.SparePartProductHolderView>(){
    open class SparePartProductHolderView(itemView: View):RecyclerView.ViewHolder(itemView)
    override fun onBindViewHolder(holder: SparePartProductHolderView, position: Int) {
        val product = products[position]
        holder.itemView.productName.text=product.name
        holder.itemView.productPrice.text=product.price.toString()
//        holder.itemView.productSupplier.text=product.supplierName
        Picasso.get().load(product.image).into(holder.itemView.productImageView)
        Picasso.get().load(product.supplierLogo).into(holder.itemView.supplier_image)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartProductHolderView {
        return SparePartProductHolderView(LayoutInflater.from(parent.context).inflate(R.layout.spare_part_details_content, parent, false))
    }
    override fun getItemCount() = products.size
}
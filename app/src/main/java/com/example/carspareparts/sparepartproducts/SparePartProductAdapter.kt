package com.example.carspareparts.sparepartproducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.setImageUrl
import kotlinx.android.synthetic.main.spare_part_details_content.view.*
import kotlinx.android.synthetic.main.spare_part_details_content.view.productImageView

/**
 * Created by rawan on 02/06/19 .
 */
class SparePartProductAdapter(private val itemClickListener: OnItemClickListener?,
                              private val sparePartDetails: List<SparePartDetails>,
                              private val clickListener:(SparePartDetails)->Boolean): RecyclerView.Adapter<SparePartProductAdapter.SparePartProductHolderView>(){
    open class SparePartProductHolderView(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(sparePartDetails: SparePartDetails, clickListener: (SparePartDetails) -> Boolean){
                clickListener(sparePartDetails)
        }
    }
    override fun onBindViewHolder(holder: SparePartProductHolderView, position: Int) {
        val sparePartItemDetails = sparePartDetails[position]
        holder.itemView.setOnClickListener {
            holder.bind(sparePartItemDetails,clickListener)
        }
        holder.itemView.productName.text=sparePartItemDetails.name
        holder.itemView.productPrice.text= "${sparePartItemDetails.price} LE"
        holder.itemView.productImageView.setImageUrl(sparePartItemDetails.image)
        holder.itemView.supplier_image.setImageUrl(sparePartItemDetails.supplierLogo)
        holder.itemView.setOnClickListener {
            itemClickListener?.onProductClick(sparePartItemDetails)
        }
        holder.itemView.buyButton.setOnClickListener {
            itemClickListener?.onAddToCartClick(sparePartItemDetails)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartProductHolderView {
        return SparePartProductHolderView(LayoutInflater.from(parent.context).inflate(R.layout.spare_part_details_content, parent, false))
    }
    override fun getItemCount() = sparePartDetails.size

    interface OnItemClickListener {

        fun onProductClick(product: SparePartDetails)

        fun onAddToCartClick(product: SparePartDetails)
    }
}
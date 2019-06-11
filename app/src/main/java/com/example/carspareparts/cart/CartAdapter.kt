package com.example.carspareparts.cart
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.parse.ParseObject
import kotlinx.android.synthetic.main.cart_content.view.*
import kotlinx.android.synthetic.main.spare_part_details_content.view.*

class CartAdapter(private val cartItems: List<ParseObject>,
                  private val clickListener:(ParseObject)->Boolean): RecyclerView.Adapter<CartAdapter.CartHolderView>(){
    open class CartHolderView(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(cartItem: ParseObject, clickListener: (ParseObject) -> Boolean){
                clickListener(cartItem)
            }
    }
    override fun onBindViewHolder(holder: CartHolderView, position: Int) {
        val cartItem = cartItems[position]
        holder.itemView.setOnClickListener {
            holder.bind(cartItem,clickListener)
        }
        holder.itemView.cartProductName.text=cartItem.getString("product_name")
        holder.itemView.cartPrice.text=cartItem.getInt("total_price").toString()
        holder.itemView.cartSupplierName.text= cartItem.getString("supplier_name")

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolderView {
        return CartHolderView(LayoutInflater.from(parent.context).inflate(R.layout.cart_content, parent, false))
    }
    override fun getItemCount() = cartItems.size
}
package com.example.carspareparts.cart
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.parse.ParseObject
import com.squareup.picasso.Picasso
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
        Picasso.get().load(cartItem.getParseObject("supplier_id")?.getString("product_image")).into(holder.itemView.cartProductImage)
        holder.itemView.cartProductName.text= holder.itemView.cartProductName.text.toString().plus( cartItem.getString("product_name"))
        holder.itemView.cartPrice.text="total price " +cartItem.getInt("total_price").times(cartItem.getInt("quantity")).toString().plus("LE")
        holder.itemView.cartSupplierName.text= holder.itemView.cartSupplierName.text.toString().plus(cartItem.getString("supplier_name"))

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolderView {
        return CartHolderView(LayoutInflater.from(parent.context).inflate(R.layout.cart_content, parent, false))
    }
    override fun getItemCount() = cartItems.size
}
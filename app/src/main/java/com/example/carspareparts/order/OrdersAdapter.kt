package com.example.carspareparts.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.parse.ParseObject
import kotlinx.android.synthetic.main.order_content.view.*
import java.text.SimpleDateFormat


class OrdersAdapter(
    private val orders: List<ParseObject>,
    private val clickListener: (ParseObject) -> Boolean
) : RecyclerView.Adapter<OrdersAdapter.OrdersHolderView>() {
    open class OrdersHolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(orders: ParseObject, clickListener: (ParseObject) -> Boolean) {
            clickListener(orders)
        }
    }

    override fun onBindViewHolder(holder: OrdersHolderView, position: Int) {
        val order = orders[position]
        holder.itemView.detailsButton.setOnClickListener {
            holder.bind(order, clickListener)
        }
        val frm = SimpleDateFormat("MMMM dd, yyyy")
        val date = order.createdAt
        holder.itemView.orderDateTextView.text = frm.format(date).toString()
        holder.itemView.orderIdTextView.text = "Order #${position.plus(1)}"
        holder.itemView.orderTotalTextView.text = order.get("total_price").toString().plus(" LE")
        holder.itemView.orderAddress.text = "Address "+ order.get("address").toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolderView {
        return OrdersHolderView(LayoutInflater.from(parent.context).inflate(R.layout.order_content, parent, false))
    }

    override fun getItemCount() = orders.size
}
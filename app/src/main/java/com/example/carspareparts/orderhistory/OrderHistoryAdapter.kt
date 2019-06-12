package com.example.carspareparts.orderhistory

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.parse.ParseObject
import kotlinx.android.synthetic.main.order_content.view.*
import java.text.SimpleDateFormat


class OrderHistoryAdapter(private val OrderHistory: List<ParseObject>,
                    private val clickListener:(ParseObject)->Boolean): RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryHolderView>(){
    open class OrderHistoryHolderView(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(OrderHistory: ParseObject, clickListener: (ParseObject) -> Boolean){
                clickListener(OrderHistory)
        }
    }
    override fun onBindViewHolder(holder: OrderHistoryHolderView, position: Int) {
        val order = OrderHistory[position]
        holder.itemView.btnDetails.setOnClickListener {
            holder.bind(order,clickListener)
        }
        var frm = SimpleDateFormat ("dd-MMMM-yyyy")
        var date = order.createdAt;
        holder.itemView.orderDate.text= frm.format(date).toString()
        holder.itemView.orderNumber.text=position.plus(1).toString()
        holder.itemView.orderTotal.text = order.get("total_price").toString()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryHolderView {
        return OrderHistoryHolderView(LayoutInflater.from(parent.context).inflate(R.layout.order_content, parent, false))
    }
    override fun getItemCount() = OrderHistory.size
}
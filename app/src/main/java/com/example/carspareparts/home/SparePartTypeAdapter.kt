package com.example.carspareparts.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.parse.ParseObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.spare_part_type_list.view.*

/**
 * Created by rawan on 02/06/19 .
 */
class SparePartTypeAdapter(private val sparePartTypeName: List<ParseObject>,
                           private val clickListener:(Pair<String,String?>)->Boolean): RecyclerView.Adapter<SparePartTypeAdapter.SparePartTypeHolderView>(){
    open class SparePartTypeHolderView(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(selectedSparePartType: String,selectedTypeName:String?, clickListener: (Pair<String,String?>) -> Boolean){
                val pair = Pair(selectedSparePartType,selectedTypeName)
                clickListener(pair)
        }
    }
    override fun onBindViewHolder(holder: SparePartTypeHolderView, position: Int) {
        val sparePartType = sparePartTypeName[position]
        holder.itemView.setOnClickListener {
            holder.bind(sparePartType.objectId,sparePartType.getString("type"),clickListener)
        }
        holder.itemView.sparePartName.text=sparePartType.getString("type")
        Picasso.get().load(sparePartType.getString("image")).into(holder.itemView.sparePartTypeIcon)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartTypeHolderView {
        return SparePartTypeHolderView(LayoutInflater.from(parent.context).inflate(R.layout.spare_part_type_list, parent, false))
    }
    override fun getItemCount() = sparePartTypeName.size
}
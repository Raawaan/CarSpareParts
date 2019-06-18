package com.example.carspareparts.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.setImageUrl
import com.parse.ParseObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.horizontal_list_item_home_category.view.*
import kotlinx.android.synthetic.main.horizontal_list_item_home_item.view.*
import kotlinx.android.synthetic.main.spare_part_type_list.view.*

class HorizontalCategoriesAdapter(private val itemClickListener: OnItemClickListener?) :
        RecyclerView.Adapter<HorizontalCategoriesAdapter.HorizontalCategoriesViewHolder>() {

    var categories: List<ParseObject>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HorizontalCategoriesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_list_item_home_category, parent, false)
    )

    override fun getItemCount() = categories?.size ?: 0

    override fun onBindViewHolder(holder: HorizontalCategoriesViewHolder, position: Int) = holder.setContent(this)

    class HorizontalCategoriesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun setContent(adapter: HorizontalCategoriesAdapter) = view.run view@ {
            val category = adapter.categories?.get(adapterPosition)
            category?.run category@ {
                val categoryId = objectId
                val categoryName = getString("type")
                val categoryImageUrl = getString("image")
                itemImageView.setImageUrl(categoryImageUrl)
                categoryNameTextView.text = categoryName
                this@view.setOnClickListener {
                    adapter.itemClickListener?.onCategoryClick(categoryId, categoryName ?: "")
                }
            }
            Unit
        }
    }

    interface OnItemClickListener {

        fun onCategoryClick(categoryId: String, categoryName: String)
    }
}
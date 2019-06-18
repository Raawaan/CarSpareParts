package com.example.carspareparts.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.carspareparts.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_pager_image.view.*

class ImagesViewPagerAdapter(private val mImagesUrls: List<String>?) : PagerAdapter() {

    override fun getCount() = mImagesUrls?.size ?: 0


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = (container
            .context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.view_pager_image, container, false)
        if (mImagesUrls != null && mImagesUrls.size > position) {
            Picasso.get()
                .load(mImagesUrls[position])
                .into(view.viewPagerImageView)
        }
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}
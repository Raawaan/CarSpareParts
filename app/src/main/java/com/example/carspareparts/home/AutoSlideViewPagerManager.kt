package com.example.carspareparts.home

import android.os.Message
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import com.example.carspareparts.R

class AutoSlideViewPagerManager private constructor(
    private var mViewPager: ViewPager?,
    private var mIndicatorLayout: LinearLayout?,
    private val mImagesUrls: List<String>?
) : ViewPager.OnPageChangeListener {
    private var mCurrentPage: Int = 0
    private lateinit var mDotsIndicator: Array<ImageView>
    private var mHandler: InnerHandler? = null

    init {
        if (mViewPager != null &&
            mIndicatorLayout != null &&
            mImagesUrls != null && mImagesUrls.isNotEmpty()
        ) {
            initialize()
        }
    }

    private fun initialize() {
        mCurrentPage = 0
        val viewPagerAdapter = ImagesViewPagerAdapter(mImagesUrls)
        mViewPager?.run {
            adapter = viewPagerAdapter
            addOnPageChangeListener(this@AutoSlideViewPagerManager)
        }
        mDotsIndicator = Array(mImagesUrls?.size ?: 0) { ImageView(mViewPager?.context) }
        mIndicatorLayout?.removeAllViews()
        mDotsIndicator.forEach { indicator ->
            indicator.setImageDrawable(
                ResourcesCompat.getDrawable(indicator.resources, R.drawable.pager_unselected, null)
            )
            mIndicatorLayout?.addView(indicator)
        }
        mIndicatorLayout?.run layout@{
            background = ResourcesCompat.getDrawable(resources, R.drawable.indicator_background, null)
        }
        setCurrentPage(mCurrentPage)
        mHandler = InnerHandler(this).apply {
            sendMessageDelayed(mHandler!!.obtainMessage(), AUTO_SLIDE_DELAY)
        }
    }

    private fun goToNextPage() {
        if (mImagesUrls != null) {
            if (mCurrentPage == mImagesUrls.size - 1) {
                mCurrentPage = -1
            }
            mCurrentPage++
            setCurrentPage(mCurrentPage)
        }
    }

    private fun updateIndicator(position: Int) {
        mDotsIndicator.forEachIndexed { index, indicator ->
            indicator.setImageDrawable(ResourcesCompat.getDrawable(
                indicator.resources,
                if (index == position)
                    R.drawable.pager_selected
                else
                    R.drawable.pager_unselected,
                null
            ))
        }
    }

    private fun setCurrentPage(position: Int) {
        if (mImagesUrls == null || position < 0 || position >= mImagesUrls.size) {
            return
        }
        mViewPager!!.setCurrentItem(mCurrentPage, true)
        updateIndicator(mCurrentPage)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        updateIndicator(position)
        mHandler?.run {
            removeCallbacksAndMessages(null)
            sendMessageDelayed(obtainMessage(), AUTO_SLIDE_DELAY)
        }
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    private class InnerHandler internal constructor(reference: AutoSlideViewPagerManager) :
        WeakReferenceHandler<AutoSlideViewPagerManager>(reference) {

        override fun handleMessage(reference: AutoSlideViewPagerManager?, msg: Message) {
            reference?.goToNextPage()
            sendMessageDelayed(obtainMessage(), AUTO_SLIDE_DELAY)
        }
    }

    fun destroy() {
        if (mHandler != null) {
            mHandler!!.removeCallbacksAndMessages(null)
            mHandler = null
        }
        mViewPager = null
        mIndicatorLayout!!.removeAllViews()
        mIndicatorLayout = null
        mDotsIndicator = arrayOf()
    }

    companion object {

        private const val AUTO_SLIDE_DELAY = 3 * 1000L

        fun initialize(
            viewPager: ViewPager,
            viewPagerIndicator: LinearLayout,
            imagesUrls: List<String>?
        ): AutoSlideViewPagerManager {
            return AutoSlideViewPagerManager(viewPager, viewPagerIndicator, imagesUrls)
        }
    }
}

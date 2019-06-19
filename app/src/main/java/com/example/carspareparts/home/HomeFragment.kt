package com.example.carspareparts.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.home.adapter.HomeSparePartProductsAdapter
import com.example.carspareparts.home.adapter.HorizontalCategoriesAdapter
import com.example.carspareparts.main.BaseFragmentInteractionListener
import com.example.carspareparts.setImageUrl
import com.parse.ParseObject
import kotlinx.android.synthetic.main.exclusive_item_card.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_view_pager.*

class HomeFragment : Fragment(),
    HorizontalCategoriesAdapter.OnItemClickListener,
    HomeSparePartProductsAdapter.OnItemClickListener {

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    private var listener: OnFragmentInteractionListener? = null

    private val viewModel: HomeFragmentViewModel by lazy { HomeFragmentViewModel.getInstance(this) }

    private val categoriesAdapter by lazy { HorizontalCategoriesAdapter(this) }
    private val productsAdapter by lazy { HomeSparePartProductsAdapter(this) }

    private var slider: AutoSlideViewPagerManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            viewPagerImages.observe(this@HomeFragment, Observer { it?.run(::showViewPagerImages) })
            categories.observe(this@HomeFragment, Observer { it?.run(::showCategories) })
            exclusiveItem.observe(this@HomeFragment, Observer { it?.run(::showExclusiveItem) })
            otherItems.observe(this@HomeFragment, Observer { it?.run(::showOtherItems) })
            isAllSet.observe(this@HomeFragment, Observer { it?.run(::showHomeData) })
            loadData()
        }
        showCategoriesButton.setOnClickListener { listener?.onAllCategoriesClick() }
        retryButton.setOnClickListener {
            loadData()
            retryButton.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun loadData() = viewModel.loadHomeData()

    private fun showHomeData(isAllSet: Boolean) {
        if (isAllSet) {
            loadingLayout.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            retryButton.visibility = View.VISIBLE
        }
    }

    private fun showViewPagerImages(viewPagerImages: List<String>) {
        slider = AutoSlideViewPagerManager.initialize(viewPager, viewPagerIndicator, viewPagerImages)
    }

    private fun showCategories(categories: List<ParseObject>) = categoriesRecyclerView.run {
        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        isNestedScrollingEnabled = false
        adapter = categoriesAdapter.apply adapter@ { this@adapter.categories = categories }
    }

    @SuppressLint("SetTextI18n")
    private fun showExclusiveItem(exclusiveItem: SparePartDetails) {
        exclusiveItemName.text = exclusiveItem.name
        exclusiveItemPrice.text = "${exclusiveItem.price} LE"
        exclusiveItemImage.setImageUrl(exclusiveItem.image)
        exclusiveItemBuyButton.setOnClickListener {
            listener?.onAddToCartClick(exclusiveItem)
        }
    }

    private fun showOtherItems(products: List<SparePartDetails>) = shoppingItemsRecyclerView.run {
        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        isNestedScrollingEnabled = false
        adapter = productsAdapter.apply adapter@ { this@adapter.sparsePartProducts = products }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        slider?.destroy()
        super.onDestroy()
    }

    override fun onCategoryClick(categoryId: String, categoryName: String) =
        listener?.onCategoryClick(categoryId, categoryName) ?: Unit

    override fun onProductClick(product: SparePartDetails) =
        listener?.onProductClick(product) ?: Unit

    override fun onAddToCartClick(product: SparePartDetails) =
        listener?.onAddToCartClick(product) ?: Unit

    interface OnFragmentInteractionListener : BaseFragmentInteractionListener {

        fun onCategoryClick(categoryId: String, categoryName: String)

        fun onAllCategoriesClick()
    }
}

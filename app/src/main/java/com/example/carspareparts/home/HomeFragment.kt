package com.example.carspareparts.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.carspareparts.R
import com.example.carspareparts.SparePartDetails
import com.example.carspareparts.home.adapter.HomeSparePartProductsAdapter
import com.example.carspareparts.home.adapter.HorizontalCategoriesAdapter
import com.example.carspareparts.main.BaseFragmentInteractionListener
import com.parse.ParseObject
import kotlinx.android.synthetic.main.exclusive_item_card.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_view_pager.*

private const val ARG_VIEW_PAGER_IMAGES = "com.example.carspareparts.home.HomeFragment.ARG_VIEW_PAGER_IMAGES"
private const val ARG_CATEGORIES = "com.example.carspareparts.home.HomeFragment.ARG_CATEGORIES"
private const val ARG_EXCLUSIVE_ITEM = "com.example.carspareparts.home.HomeFragment.ARG_EXCLUSIVE_ITEM"
private const val ARG_OTHER_ITEMS = "com.example.carspareparts.home.HomeFragment.ARG_OTHER_ITEMS"

class HomeFragment : Fragment(),
    HorizontalCategoriesAdapter.OnItemClickListener,
    HomeSparePartProductsAdapter.OnItemClickListener {

    private var listener: OnFragmentInteractionListener? = null

    private var viewPagerImages: List<String>? = null
    private var categories: List<ParseObject>? = null
    private var exclusiveItem: SparePartDetails? = null
    private var otherItems: List<SparePartDetails>? = null

    private val categoriesAdapter by lazy { HorizontalCategoriesAdapter(this) }
    private val productsAdapter by lazy { HomeSparePartProductsAdapter(this) }

    private lateinit var slider: AutoSlideViewPagerManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            viewPagerImages =
                if (containsKey(ARG_VIEW_PAGER_IMAGES)) getStringArrayList(ARG_VIEW_PAGER_IMAGES)
                else null
            categories =
                if (containsKey(ARG_CATEGORIES)) getParcelableArrayList(ARG_CATEGORIES)
                else null
            exclusiveItem =
                if (containsKey(ARG_EXCLUSIVE_ITEM)) getParcelable(ARG_EXCLUSIVE_ITEM)
                else null
            otherItems =
                if (containsKey(ARG_OTHER_ITEMS)) getParcelableArrayList(ARG_OTHER_ITEMS)
                else null
        }
        viewPagerImages?.run(::showViewPagerImages)
        categories?.run(::showCategories)
        exclusiveItem?.run(::showExclusiveItem)
        otherItems?.run(::showOtherItems)
        showCategoriesButton.setOnClickListener {
            listener?.onAllCategoriesClick()
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
        slider.destroy()
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

    companion object {

        @JvmStatic
        fun newInstance(
            pagerImages: ArrayList<String> = arrayListOf(),
            categories: ArrayList<ParseObject> = arrayListOf(),
            exclusiveItem: SparePartDetails? = null,
            otherItems: ArrayList<SparePartDetails> = arrayListOf()
        ) = HomeFragment().apply {
            arguments = Bundle().apply {
                putStringArrayList(ARG_VIEW_PAGER_IMAGES, pagerImages)
                putParcelableArrayList(ARG_CATEGORIES, categories)
                putParcelable(ARG_EXCLUSIVE_ITEM, exclusiveItem)
                putParcelableArrayList(ARG_OTHER_ITEMS, otherItems)
            }
        }
    }
}

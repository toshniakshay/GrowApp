package com.android.growappdemo.UI.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import com.android.growappdemo.Adapters.ProductsAdapter
import com.android.growappdemo.Adapters.ProductsAdapter.ProductEventHandler
import com.android.growappdemo.Models.Http.DataResponse
import com.android.growappdemo.Models.Http.Product
import com.android.growappdemo.R
import com.android.growappdemo.UI.Activities.MainActivity
import com.android.growappdemo.Utils.Utility
import com.android.growappdemo.WebAPIs.ProductRepoProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class ProductListFragment : Fragment(), ProductEventHandler, View.OnClickListener, TextWatcher, FilterDialogFragment.FilterSelectionListener {

    companion object {
        val CLASSTAG: String? = ProductListFragment.javaClass.simpleName
    }

    private var productList: RecyclerView? = null
    private var searchQuery: EditText? = null
    private var result: DataResponse? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.frag_product_list, container, false)
        initView(view)
        return view
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onFilterSelected(selection: Int) {
        updateList(selection)
    }

    override fun onProductClick(product: Product?) {
        val productDetailsFrag = ProductDetailsFragment()
        productDetailsFrag.selectedProduct = product

        (activity as MainActivity).loadFragment(productDetailsFrag, ProductDetailsFragment.CLASSTAG!!, true)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.filter_items -> {
                val filter = FilterDialogFragment()
                val fragmentManager = activity.fragmentManager
                filter.filerSelecrtion = this
                filter.show(fragmentManager, FilterDialogFragment.CLASSTAG)
            }
        }
    }

    private fun initView(view: View) {

        if (!Utility().isNetworkAvailable(activity)) {
            showDialog()
        }
        productList = view.findViewById<RecyclerView>(R.id.product_list)

        view.findViewById<ImageButton>(R.id.filter_items).setOnClickListener(this)

        view.findViewById<ProgressBar>(R.id.progerss).visibility = View.VISIBLE
        getProducts()

        searchQuery = view.findViewById<EditText>(R.id.search_items)
        searchQuery?.addTextChangedListener(this)
    }

    private fun showDialog() {
        AlertDialog.Builder(activity)
                .setTitle("Grow App Error")
                .setMessage("Make sure you have active internet connection")
                .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss();activity.finish() }).show()
    }

    private fun getProducts() {

        val repository = ProductRepoProvider.provideProductRepository()
        repository.getProducts().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({ result ->
            this.result = result
            view!!.findViewById<ProgressBar>(R.id.progerss).visibility = View.GONE
            initAdapter()
        }, { error ->
            {
                view!!.findViewById<ProgressBar>(R.id.progerss).visibility = View.GONE
            }
            error.printStackTrace()
        })
    }

    /**
     * After API calling is finished init adapter with response
     */
    private fun initAdapter() {
        val productsAdapter = ProductsAdapter(result?.products, activity)
        productsAdapter.setProductListener(this)
        productList?.layoutManager = LinearLayoutManager(activity)
        productList?.adapter = productsAdapter
        productsAdapter.notifyDataSetChanged()
    }

    /**
     * After filter is applied update List as per selection
     */
    private fun updateList(selection: Int) {
        when (selection) {
            FilterDialogFragment.SHOW_ALL -> {
                showAllProducts()
            }
            FilterDialogFragment.SHOW_BY_DISC -> {
                showDiscountedProducts()
            }
            FilterDialogFragment.SHOW_BY_SALE -> {
                showProductsOnSale()
            }
        }
    }

    private fun showProductsOnSale() {
        if (result != null) {
            val productsOnSale: ArrayList<Product> = ArrayList()
            for (product: Product in result!!.products)
                if (product.onSale)
                    productsOnSale.add(product)

            if (productsOnSale.size > 0) {
                val productsAdapter = ProductsAdapter(productsOnSale, activity)
                productsAdapter.setProductListener(this)
                productList?.layoutManager = LinearLayoutManager(activity)

                productList?.adapter = productsAdapter

                productsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showDiscountedProducts() {
        if (result != null) {
            val productsOnDiscount: ArrayList<Product> = ArrayList()
            for (product: Product in result!!.products) {
                if (!TextUtils.isEmpty(product.discountPercent)) {
                    val dis: String? = product.discountPercent;
                    val discount = dis!!.substring(0, dis.length.minus(1))
                    if (java.lang.Double.parseDouble(discount) != 0.0)
                        productsOnDiscount.add(product)
                }
            }
            if (productsOnDiscount.size > 0) {
                val productsAdapter = ProductsAdapter(productsOnDiscount, activity)
                productsAdapter.setProductListener(this)
                productList?.layoutManager = LinearLayoutManager(activity)
                productList?.adapter = productsAdapter
                productsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showAllProducts() {
        if (result != null) {
            val productsAdapter = ProductsAdapter(result!!.products, activity)
            productsAdapter.setProductListener(this)
            productList?.layoutManager = LinearLayoutManager(activity)
            productList?.adapter = productsAdapter
            productsAdapter.notifyDataSetChanged()
        }
    }
}
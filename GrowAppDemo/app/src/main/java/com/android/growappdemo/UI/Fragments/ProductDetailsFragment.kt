package com.android.growappdemo.UI.Fragments

import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.growappdemo.Adapters.SizeAdapter
import com.android.growappdemo.Models.Http.Product
import com.android.growappdemo.Models.Http.Size
import com.android.growappdemo.R
import com.bumptech.glide.Glide

class ProductDetailsFragment : Fragment(), SizeAdapter.SizeEventWatcher {

    companion object {
        val CLASSTAG : String? = ProductDetailsFragment.javaClass.simpleName;
    }

    var selectedProduct : Product? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View = inflater!!.inflate(R.layout.frag_product_details, container, false)
        initView(view)
        return  view
    }

    override fun onSizeSelected(size: Size?) {
        //TODO::size selections
    }

    override fun onSizeDeselected(size: Size?) {
        //TODO:: size de-selections
    }

    /**
     * Init UI components
     */
    private fun initView(view: View) {
        if (selectedProduct == null) {
            Toast.makeText(activity, "Error occurred while loading Item details", Toast.LENGTH_SHORT).show()
            activity.supportFragmentManager.popBackStack()
        }

        Glide.with(context).load(selectedProduct?.image).into( view.findViewById<ImageView>(R.id.product_image))
        val sale = view.findViewById<TextView>(R.id.sale_text)

        when {
            selectedProduct!!.onSale -> sale.visibility = View.VISIBLE
            else -> sale.visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.product_name).text = selectedProduct!!.name

        val reularPriceview = view.findViewById<TextView>(R.id.product_price)
        val regPrice = selectedProduct!!.regualrPrice

        val actualPriceview = view.findViewById<TextView>(R.id.sale_price)
        val actPrice = selectedProduct!!.actualPrice

        if (regPrice.equals(actPrice, true)) {
            actualPriceview.visibility = View.GONE
        } else {
            actualPriceview.visibility = View.VISIBLE
            reularPriceview.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        reularPriceview.text = regPrice


        view.findViewById<TextView>(R.id.installment_options).text = selectedProduct!!.installment

        showAvaialableSizes(view)

    }

    /**
     * Get Available sizes for product and show it in horizontal recycler view
     */
    private fun showAvaialableSizes(view: View) {
        val sizeList = view.findViewById<RecyclerView>(R.id.size_list)
        val sizeAdapter = SizeAdapter(activity,selectedProduct?.sizes)
        sizeList?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        sizeList?.adapter = sizeAdapter
        sizeAdapter.setSizeListener(this)
        sizeAdapter.notifyDataSetChanged()
    }
}
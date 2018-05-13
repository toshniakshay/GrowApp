package com.android.growappdemo.Models.Http

import com.android.growappdemo.Utils.GAppConstants
import com.google.gson.annotations.SerializedName


data class DataResponse(@SerializedName(GAppConstants.products)val products : List<Product>)

data class Product(@SerializedName(GAppConstants.name)val name: String,
                   @SerializedName(GAppConstants.style)val style : String,
                   @SerializedName(GAppConstants.code_color)val codeColor: String,
                   @SerializedName(GAppConstants.color_slug)val colorSlug : String,
                   @SerializedName(GAppConstants.color)val color : String,
                   @SerializedName(GAppConstants.on_sale)val onSale : Boolean,
                   @SerializedName(GAppConstants.regular_price)val regualrPrice: String,
                   @SerializedName(GAppConstants.actual_price)val actualPrice : String,
                   @SerializedName(GAppConstants.discount_percentage)val discountPercent : String,
                   @SerializedName(GAppConstants.installments)val installment : String,
                   @SerializedName(GAppConstants.image)val image : String,
                   @SerializedName(GAppConstants.sizes)val sizes : List<Size>)

data class Size(@SerializedName(GAppConstants.available)val available : Boolean,
                @SerializedName(GAppConstants.size)val size : String,
                @SerializedName(GAppConstants.sku)val sku : String)

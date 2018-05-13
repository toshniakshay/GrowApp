package com.android.growappdemo.WebAPIs

import com.android.growappdemo.Models.Http.DataResponse
import io.reactivex.Observable

class ProductsRepositoy(val apiService : GetProductsInterface) {

    fun getProducts() : Observable<DataResponse> {
        return apiService.getProducts()
    }

}
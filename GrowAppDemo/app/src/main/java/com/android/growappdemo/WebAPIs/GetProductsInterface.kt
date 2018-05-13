package com.android.growappdemo.WebAPIs

import com.android.growappdemo.Models.Http.DataResponse
import com.android.growappdemo.Utils.GAppConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit


public interface GetProductsInterface {

    @GET(GAppConstants.GET_PRODUCTS_URI)
    fun getProducts(): Observable<DataResponse>

    /**
     * Factory class for convenient creation of the Api Service interface
     */
    companion object Factory {

        fun create(): GetProductsInterface {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GAppConstants.BASE_URL)
                    .build()

            return retrofit.create<GetProductsInterface>(GetProductsInterface::class.java)
        }
    }
}
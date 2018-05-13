package com.android.growappdemo.WebAPIs

object ProductRepoProvider {

    fun provideProductRepository() : ProductsRepositoy {
        return ProductsRepositoy(GetProductsInterface.Factory.create())
    }
}
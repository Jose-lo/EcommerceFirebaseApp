package com.example.nellymakeup.repository

import android.app.Activity
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.ui.activities.DetailsActivity

class RepositoryImpl(private val remoteDataSource: RemoteDataSource): Repository {

    override suspend fun getDashboardItemsList(): Resource<List<Product>> {
        return remoteDataSource.getDashboardItemsList()
    }

    override suspend fun getProductDetails(activity: DetailsActivity, productId: String) {
        remoteDataSource.getProductDetails(activity,productId)
    }

    override suspend fun addCartItems(activity: DetailsActivity, addToCart: CartItem) {
        remoteDataSource.addCartItems(activity,addToCart)
    }

    override suspend fun checkIfItemExistInCart(activity: DetailsActivity, productId: String) {
        remoteDataSource.checkIfItemExistInCart(activity,productId)
    }

    override suspend fun getCartList(activity: Activity) {
        remoteDataSource.getCartList(activity)
    }
}
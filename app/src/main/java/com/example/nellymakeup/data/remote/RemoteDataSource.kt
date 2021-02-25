package com.example.nellymakeup.data.remote

import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.ui.activities.DetailsActivity

class RemoteDataSource(private val firestoreClass: FirestoreClass) {

    suspend fun getDashboardItemsList(): Resource<List<Product>>{
        return firestoreClass.getDashboardItemsList()
    }

    suspend fun getProductDetails(activity: DetailsActivity, productId: String){
        firestoreClass.getProductDetails(activity,productId)
    }

    suspend fun addCartItems(activity: DetailsActivity, addToCart: CartItem){
        firestoreClass.addCartItems(activity,addToCart)
    }
}
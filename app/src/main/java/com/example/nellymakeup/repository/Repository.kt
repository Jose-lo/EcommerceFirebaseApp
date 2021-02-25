package com.example.nellymakeup.repository

import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.ui.activities.DetailsActivity

interface Repository {

    suspend fun getDashboardItemsList(): Resource<List<Product>>
    suspend fun getProductDetails(activity: DetailsActivity, productId: String)
    suspend fun addCartItems(activity: DetailsActivity, addToCart: CartItem)
}
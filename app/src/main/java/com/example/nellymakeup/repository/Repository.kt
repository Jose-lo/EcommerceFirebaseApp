package com.example.nellymakeup.repository

import android.app.Activity
import android.content.Context
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.ui.activities.AddEditAddressActivity
import com.example.nellymakeup.ui.activities.DetailsActivity

interface Repository {

    suspend fun getDashboardItemsList(): Resource<List<Product>>
    suspend fun getProductDetails(activity: DetailsActivity, productId: String)
    suspend fun addCartItems(activity: DetailsActivity, addToCart: CartItem)
    suspend fun checkIfItemExistInCart(activity: DetailsActivity, productId: String)
    suspend fun getCartList(activity: Activity)
    suspend fun getAllProductsList(activity: Activity)
    suspend fun removeItemFromCart(context: Context, cart_id: String)
    suspend fun updateMyCart(context: Context, cart_id: String, itemHashMap: HashMap<String, Any>)
    suspend fun getUserDetails(activity: Activity)
    suspend fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>)
    suspend fun addAddress(activity: AddEditAddressActivity, addressInfo: Address)
}
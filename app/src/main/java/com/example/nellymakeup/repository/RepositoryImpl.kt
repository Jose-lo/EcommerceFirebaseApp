package com.example.nellymakeup.repository

import android.app.Activity
import android.content.Context
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.ui.activities.AddEditAddressActivity
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

    override suspend fun getAllProductsList(activity: Activity) {
        remoteDataSource.getAllProductsList(activity)
    }

    override suspend fun removeItemFromCart(context: Context, cart_id: String) {
        remoteDataSource.removeItemFromCart(context,cart_id)
    }

    override suspend fun updateMyCart(
        context: Context,
        cart_id: String,
        itemHashMap: HashMap<String, Any>
    ) {
        remoteDataSource.updateMyCart(context,cart_id,itemHashMap)
    }

    override suspend fun getUserDetails(activity: Activity){
        remoteDataSource.getUserDetails(activity)
    }

    override suspend fun updateUserProfileData(
        activity: Activity,
        userHashMap: HashMap<String, Any>
    ) {
        remoteDataSource.updateUserProfileData(activity,userHashMap)
    }

    override suspend fun addAddress(activity: AddEditAddressActivity, addressInfo: Address) {
        remoteDataSource.addAddress(activity,addressInfo)
    }
}
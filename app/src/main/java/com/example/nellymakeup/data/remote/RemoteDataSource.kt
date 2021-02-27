package com.example.nellymakeup.data.remote

import android.app.Activity
import android.content.Context
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.ui.activities.AddEditAddressActivity
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

    suspend fun checkIfItemExistInCart(activity: DetailsActivity, productId: String){
        firestoreClass.checkIfItemExistInCart(activity, productId)
    }

    suspend fun getCartList(activity: Activity){
        firestoreClass.getCartList(activity)
    }

    suspend fun getAllProductsList(activity: Activity){
        firestoreClass.getAllProductsList(activity)
    }

    suspend fun removeItemFromCart(context: Context, cart_id: String){
        firestoreClass.removeItemFromCart(context,cart_id)
    }

    suspend fun updateMyCart(context: Context, cart_id: String, itemHashMap: HashMap<String, Any>){
        firestoreClass.updateMyCart(context,cart_id,itemHashMap)
    }

    suspend fun getUserDetails(activity: Activity){
        firestoreClass.getUserDetails(activity)
    }

    suspend fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        firestoreClass.updateUserProfileData(activity,userHashMap)
    }

    suspend fun addAddress(activity: AddEditAddressActivity, addressInfo: Address){
        firestoreClass.addAddress(activity,addressInfo)
    }
}
package com.example.nellymakeup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.repository.Repository
import com.example.nellymakeup.ui.activities.DetailsActivity
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repo: Repository): ViewModel() {

    fun getProductDetails(activity: DetailsActivity, productId: String){
        viewModelScope.launch {
            repo.getProductDetails(activity,productId)
        }
    }

    fun addCartItems(activity: DetailsActivity, addToCart: CartItem){
        viewModelScope.launch {
            repo.addCartItems(activity,addToCart)
        }
    }

    fun checkIfItemExistInCart(activity: DetailsActivity, productId: String){
        viewModelScope.launch {
            repo.checkIfItemExistInCart(activity,productId)
        }
    }
}
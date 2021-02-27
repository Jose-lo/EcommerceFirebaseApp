package com.example.nellymakeup.presentation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.repository.Repository
import kotlinx.coroutines.launch

class CartListScreenViewModel(private val repo: Repository):ViewModel() {

    fun getCartList(activity: Activity){
        viewModelScope.launch {
            repo.getCartList(activity)
        }
    }

    fun getAllProductsList(activity: Activity){
        viewModelScope.launch {
            repo.getAllProductsList(activity)
        }
    }

    fun removeItemFromCart(context: Context, cart_id: String){
        viewModelScope.launch {
            repo.removeItemFromCart(context,cart_id)
        }
    }
}
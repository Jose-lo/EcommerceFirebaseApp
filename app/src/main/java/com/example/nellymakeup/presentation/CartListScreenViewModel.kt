package com.example.nellymakeup.presentation

import android.app.Activity
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
}
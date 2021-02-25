package com.example.nellymakeup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.repository.Repository
import com.example.nellymakeup.ui.activities.DetailsActivity
import kotlinx.coroutines.launch

class DetailScreenModel(private val repo: Repository): ViewModel() {

    fun getProductDetails(activity: DetailsActivity, productId: String){
        viewModelScope.launch {
            repo.getProductDetails(activity,productId)
        }
    }
}
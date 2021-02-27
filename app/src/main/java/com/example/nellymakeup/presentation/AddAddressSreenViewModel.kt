package com.example.nellymakeup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.repository.Repository
import com.example.nellymakeup.ui.activities.AddEditAddressActivity
import kotlinx.coroutines.launch

class AddAddressSreenViewModel(private val repo: Repository): ViewModel() {

    fun addAddress(activity: AddEditAddressActivity, addressInfo: Address){
        viewModelScope.launch {
            repo.addAddress(activity,addressInfo)
        }
    }
}
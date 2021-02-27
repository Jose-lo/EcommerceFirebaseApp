package com.example.nellymakeup.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.repository.Repository
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val repo: Repository): ViewModel() {

    fun getUserDetails(activity: Activity){
        viewModelScope.launch {
            repo.getUserDetails(activity)
        }
    }
}
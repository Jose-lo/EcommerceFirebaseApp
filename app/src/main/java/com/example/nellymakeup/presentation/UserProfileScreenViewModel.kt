package com.example.nellymakeup.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nellymakeup.repository.Repository
import kotlinx.coroutines.launch

class UserProfileScreenViewModel(private val repo: Repository): ViewModel() {

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        viewModelScope.launch {
            repo.updateUserProfileData(activity,userHashMap)
        }
    }
}
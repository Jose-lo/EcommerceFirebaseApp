package com.example.nellymakeup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.repository.Repository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeScreenViewModel(private val repo: Repository): ViewModel() {

    fun fetchDashboardList()= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getDashboardItemsList())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}
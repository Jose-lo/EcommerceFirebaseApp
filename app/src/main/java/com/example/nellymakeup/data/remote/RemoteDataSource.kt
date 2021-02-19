package com.example.nellymakeup.data.remote

import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Product

class RemoteDataSource(private val firestoreClass: FirestoreClass) {

    suspend fun getDashboardItemsList(): Resource<List<Product>>{
        return firestoreClass.getDashboardItemsList()
    }
}
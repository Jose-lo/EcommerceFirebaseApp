package com.example.nellymakeup.repository

import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.RemoteDataSource

class RepositoryImpl(private val remoteDataSource: RemoteDataSource): Repository {

    override suspend fun getDashboardItemsList(): Resource<List<Product>> {
        return remoteDataSource.getDashboardItemsList()
    }
}
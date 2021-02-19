package com.example.nellymakeup.repository

import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.data.model.Product

interface Repository {

    suspend fun getDashboardItemsList(): Resource<List<Product>>
}
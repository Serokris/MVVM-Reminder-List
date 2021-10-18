package com.example.data.repository

import com.example.data.mappers.toClientList
import com.example.data.source.remote.RandomUserApiService
import com.example.domain.models.Client
import com.example.domain.repository.RandomUserServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RandomUserServiceRepositoryImpl(
    private val apiService: RandomUserApiService
) : RandomUserServiceRepository {

    override suspend fun getRandomClients(count: Int): List<Client> {
        return withContext(Dispatchers.IO) {
            apiService.getRandomClients(count).body()?.toClientList()!!
        }
    }
}
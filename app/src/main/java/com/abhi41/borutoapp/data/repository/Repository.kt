package com.abhi41.borutoapp.data.repository

import androidx.paging.PagingData
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.domain.repository.DataStoreOperations
import com.abhi41.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {
    suspend fun saveOnBoardingState(completed: Boolean){
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean>{
        return dataStore.readOnBoardingState()
    }

    fun getAllHeroes(): Flow<PagingData<Hero>> {
        return remoteDataSource.getAllHeroes()
    }

    fun searchHeroes(query:String): Flow<PagingData<Hero>>{
        return remoteDataSource.searchHeroes(query = query)
    }
}
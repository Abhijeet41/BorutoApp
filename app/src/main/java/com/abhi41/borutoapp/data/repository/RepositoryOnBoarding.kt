package com.abhi41.borutoapp.data.repository

import androidx.datastore.core.DataStore
import com.abhi41.borutoapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryOnBoarding @Inject constructor(
    private val dataStore: DataStoreOperations
) {
    suspend fun saveOnBoardingState(completed: Boolean){
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean>{
        return dataStore.readOnBoardingState()
    }
}
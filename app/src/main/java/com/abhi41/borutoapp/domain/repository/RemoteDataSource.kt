package com.abhi41.borutoapp.domain.repository

import androidx.paging.PagingData
import com.abhi41.borutoapp.data.local.entity.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<Hero>>
    fun searchHeroes(): Flow<PagingData<Hero>>
}
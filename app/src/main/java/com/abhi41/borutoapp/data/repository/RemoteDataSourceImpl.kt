package com.abhi41.borutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhi41.borutoapp.data.local.BorutoDatabase
import com.abhi41.borutoapp.data.paging_source.HeroRemoteMediator
import com.abhi41.borutoapp.data.remote.BorutoApi
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.data.paging_source.SearchHeroesSource
import com.abhi41.borutoapp.domain.repository.RemoteDataSource
import com.abhi41.borutoapp.util.Constants
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {
    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllheroes() }
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
             //   initialLoadSize = Constants.ITEMS_PER_PAGE * 3 this is system default we don't neet to add this
            ),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSource(borutoApi = borutoApi, query = query)
            }
        ).flow
    }


}
package com.abhi41.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.abhi41.borutoapp.data.local.BorutoDatabase
import com.abhi41.borutoapp.data.remote.BorutoApi
import com.abhi41.borutoapp.domain.model.Hero
import com.abhi41.borutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {


    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeys()

    /*
        what load function do?
        1) Determine which page to load from the network depending on load type and the data that
        has been loaded so far
        2) Trigger the network request.
     */

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> { //content updates, or the initial load
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    //remoteKeys database is empty then we return 1 by default
                    remoteKeys?.nextPage?.minus(1) ?: 1 //default page is 1
                }
                LoadType.PREPEND -> { //Load at the start of a PagingData.
                    val remoteKeys = getRemoteKeyForFirstItem(state)//we will get first hero id
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            //if prevPage is null then we return MediatorResult to our load fun
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage //if prevPage is not null then return prevPage

                }
                LoadType.APPEND -> { //Load at the end of a PagingData.
                    val remoteKeys = getRemoteKeyForLasttItem(state) //we will get last hero id
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            //if nextPage is null then we return MediatorResult to our load fun
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage //if nextPage is not null then return null
                }
            }

            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                /*withTransaction: Room will only perform at most one transaction at a time,
                 additional transactions are queued and executed on a first come, first serve order.*/
                borutoDatabase.withTransaction {
                    //when invalidating data delete existing hero table and hero_remote_keys table
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                    heroDao.addHeroes(heros = response.heroes)
                }

            }
            //this means query exhausted
            return MediatorResult.Success(endOfPaginationReached = response.nextPage == null)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        //anchorPosition: Most recently accessed index in the list
        return state.anchorPosition?.let { position: Int ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
            }
    }

    private suspend fun getRemoteKeyForLasttItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
            }
    }
        /*
            Remember paging state contains information
             about the pages loaded so far
         */

}
package com.abhi41.borutoapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.data.remote.BorutoApi
import javax.inject.Inject

/*
    Note: Roomdatabase Doesn't need paging source
    e.g @Query("SELECT * FROM hero_table ORDER BY id ASC")
fun getAllheroes(): PagingSource<Int, Hero>

but for api we need to create separate paging source i.e SearchHeroesSource
 */
class SearchHeroesSource @Inject constructor(
    private val borutoApi: BorutoApi,
    private val query: String //query pass from search screen
) : PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        try {
            val apiResponse = borutoApi.searchHeroes(name = query)
            val heroes = apiResponse.heroes

            if (heroes.isNotEmpty()) {
                return LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }

}
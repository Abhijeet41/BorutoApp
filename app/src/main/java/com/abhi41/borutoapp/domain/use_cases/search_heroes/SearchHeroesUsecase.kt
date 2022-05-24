package com.abhi41.borutoapp.domain.use_cases.search_heroes

import androidx.paging.PagingData
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class SearchHeroesUsecase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Hero>>{
        return repository.searchHeroes(query = query)
    }
}
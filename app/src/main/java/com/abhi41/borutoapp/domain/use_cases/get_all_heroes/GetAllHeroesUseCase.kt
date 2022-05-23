package com.abhi41.borutoapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.abhi41.borutoapp.data.repository.Repository
import com.abhi41.borutoapp.data.local.entity.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Hero>>{
        return repository.getAllHeroes()
    }
}
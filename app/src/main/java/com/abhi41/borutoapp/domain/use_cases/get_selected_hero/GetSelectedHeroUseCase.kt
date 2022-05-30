package com.abhi41.borutoapp.domain.use_cases.get_selected_hero

import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.data.repository.Repository

class GetSelectedHeroUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Hero{
        return repository.getSelectedHero(heroId = heroId)
    }
}
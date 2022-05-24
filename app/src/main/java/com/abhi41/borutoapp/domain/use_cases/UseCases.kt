package com.abhi41.borutoapp.domain.use_cases

import com.abhi41.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.abhi41.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.abhi41.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.abhi41.borutoapp.domain.use_cases.search_heroes.SearchHeroesUsecase


data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroesUsecase: SearchHeroesUsecase
)



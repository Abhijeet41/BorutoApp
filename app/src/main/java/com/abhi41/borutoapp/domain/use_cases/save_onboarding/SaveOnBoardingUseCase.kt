package com.abhi41.borutoapp.domain.use_cases.save_onboarding

import com.abhi41.borutoapp.data.repository.RepositoryOnBoarding

class SaveOnBoardingUseCase(
    private val repositoryOnBoarding: RepositoryOnBoarding
) {
    suspend operator fun invoke(completed: Boolean) {
        repositoryOnBoarding.saveOnBoardingState(completed)
    }
}
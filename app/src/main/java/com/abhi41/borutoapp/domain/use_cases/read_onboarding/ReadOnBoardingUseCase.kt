package com.abhi41.borutoapp.domain.use_cases.read_onboarding

import com.abhi41.borutoapp.data.repository.RepositoryOnBoarding
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(
    private val repositoryOnBoarding: RepositoryOnBoarding
) {
    /*
        IMP Note: from SplashViewModel we called this ReadOnBoardingUserCase and we haven't called
        this invoke function explicitly so. because The main reason we used Operator function which
        allows us to call this function just by calling our class
     */

    operator fun invoke(): Flow<Boolean>{
        return repositoryOnBoarding.readOnBoardingState()
    }
}
package com.abhi41.borutoapp.domain.use_cases.read_onboarding

import com.abhi41.borutoapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(
    private val repository: Repository
) {
    /*
        IMP Note: from SplashViewModel we called this ReadOnBoardingUserCase and we haven't called
        this invoke function explicitly so. because The main reason we used Operator function which
        allows us to call this function just by calling our class
     */

    operator fun invoke(): Flow<Boolean>{
        return repository.readOnBoardingState()
    }
}
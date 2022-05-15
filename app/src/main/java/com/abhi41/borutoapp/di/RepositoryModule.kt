package com.abhi41.borutoapp.di

import android.content.Context
import com.abhi41.borutoapp.data.repository.DataStoreOperationsImpl
import com.abhi41.borutoapp.data.repository.RepositoryOnBoarding
import com.abhi41.borutoapp.domain.repository.DataStoreOperations
import com.abhi41.borutoapp.domain.use_cases.UseCases
import com.abhi41.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.abhi41.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /*
        Note when ever we try to inject data stored operations we are going
        to provide DataStoreOperationImpl
         */
    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }


    @Provides
    @Singleton //this will provide instance of usecases
    fun provideUseCases(repositoryOnBoarding: RepositoryOnBoarding): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repositoryOnBoarding),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repositoryOnBoarding)
        )
    }

}
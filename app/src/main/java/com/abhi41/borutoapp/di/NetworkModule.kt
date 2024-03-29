package com.abhi41.borutoapp.di

import androidx.paging.ExperimentalPagingApi
import com.abhi41.borutoapp.data.local.BorutoDatabase
import com.abhi41.borutoapp.data.remote.BorutoApi
import com.abhi41.borutoapp.data.repository.RemoteDataSourceImpl
import com.abhi41.borutoapp.domain.repository.RemoteDataSource
import com.abhi41.borutoapp.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton  //this will provide how to create OkHttpClient instance
    fun provideHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            //.addConverterFactory(Json.asConverterFactory(contentType)) //this not working
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi{
        return retrofit.create(BorutoApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRemoteDatabase(
        borutoApi: BorutoApi,
        borutoDatabase: BorutoDatabase
    ):RemoteDataSource{
        return RemoteDataSourceImpl(
            borutoApi = borutoApi,
            borutoDatabase = borutoDatabase
        )
    }
}
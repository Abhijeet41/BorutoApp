package com.abhi41.borutoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhi41.borutoapp.data.local.BorutoDatabase
import com.abhi41.borutoapp.data.local.BorutoDatabase.Companion.migration_1_2
import com.abhi41.borutoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase {
        return Room.databaseBuilder(
            context,
            BorutoDatabase::class.java,
            Constants.BORUTO_DATABASE
        ).addMigrations(migration_1_2)
            //.fallbackToDestructiveMigration()
            .build()
    }

}
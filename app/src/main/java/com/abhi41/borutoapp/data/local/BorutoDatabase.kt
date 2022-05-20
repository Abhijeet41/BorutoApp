package com.abhi41.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi41.borutoapp.data.local.dao.HeroDao
import com.abhi41.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.abhi41.borutoapp.domain.model.Hero
import com.abhi41.borutoapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeys(): HeroRemoteKeysDao
}
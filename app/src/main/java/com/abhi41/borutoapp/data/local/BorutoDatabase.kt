package com.abhi41.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abhi41.borutoapp.data.local.dao.HeroDao
import com.abhi41.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.data.local.entity.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 2)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeys(): HeroRemoteKeysDao

    companion object{
        val migration_1_2: Migration = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE hero_remote_keys_table ADD COLUMN lastUpdated INTEGER DEFAULT NULL")
            }
        }
    }

}
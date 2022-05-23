package com.abhi41.borutoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.borutoapp.util.Constants

@Entity(tableName = Constants.HERO_REMOTE_KEYS_DATABSE_TABLE)
data class HeroRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated:Long?
)

package com.abhi41.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.borutoapp.util.Constants

@Entity(tableName = Constants.HERO_REMOTE_KEY_DATABSE_TABLE)
data class HeroRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)

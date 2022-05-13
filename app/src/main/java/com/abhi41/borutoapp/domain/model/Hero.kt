package com.abhi41.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.borutoapp.util.Constants

@Entity(tableName = Constants.HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val name:String,
    val image:String,
    val about:String,
    val rating:Double,
    val power:Int,
    val month:String,
    val day:String,
    // our roomDb doen't know how to save these list of string so use TypeConverter
    val family:List<String>,
    val abilities:List<String>,
    val natureTypes:List<String>,
)

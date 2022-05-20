package com.abhi41.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.borutoapp.util.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("power")
    val power: Int,
    @SerializedName("month")
    val month: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("family")
    val family: List<String>,
    @SerializedName("abilities")
    val abilities: List<String>,
    @SerializedName("natureTypes")
    val natureTypes: List<String>// our roomDb doen't know how to save these list of string so use TypeConverter

)

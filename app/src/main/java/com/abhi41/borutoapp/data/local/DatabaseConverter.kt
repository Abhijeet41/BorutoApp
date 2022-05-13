package com.abhi41.borutoapp.data.local

import androidx.room.TypeConverter
import java.lang.StringBuilder

class DatabaseConverter {

    private val seperator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val stringBuilder = StringBuilder()

        for (item in list) {
            stringBuilder.append(list).append(seperator)
        }
        //so the problem is we have list of heroes i.e thor,loki,dr strange,
        // we need to remove last ',' from item dr strange
        stringBuilder.setLength(stringBuilder.length - seperator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(item: String): List<String> {
        return item.split(seperator)
    }

}
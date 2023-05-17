package com.ralphevmanzano.movies.data.datasource.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MoviesTypeConverters {

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toIntList(jsonString: String): List<Int> {
        return Json.decodeFromString(jsonString)
    }
}
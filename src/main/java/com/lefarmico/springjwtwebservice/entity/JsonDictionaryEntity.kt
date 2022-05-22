package com.lefarmico.springjwtwebservice.entity

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import org.springframework.core.io.Resource
import java.io.File

data class Dictionary(
    val languageName: String,
    @SerializedName("categoryList") val fileCategoryList: List<FileCategory>
)

data class FileCategory(
    val categoryName: String,
    @SerializedName("wordsList") val wordsList: List<FileWord>
)

data class FileWord(
    val wordOriginal: String,
    val wordTranslation: String,
)

fun getDictionaryFromResource(res: Resource): Dictionary {
    res.inputStream.bufferedReader().use {
        try {
            return Gson().fromJson(it.readText(), Dictionary::class.java)
        } catch (e: JsonParseException) {
            throw e
        }
    }
}

fun getDictionaryFromJson(jsonPath: String): Dictionary {
    File(jsonPath).bufferedReader().use {
        try {
            return Gson().fromJson(it.readText(), Dictionary::class.java)
        } catch (e: JsonParseException) {
            throw e
        }
    }
}

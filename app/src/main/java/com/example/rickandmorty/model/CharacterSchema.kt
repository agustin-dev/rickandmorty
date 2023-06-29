package com.example.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.rickandmorty.model.Constants.CHARACTER_TABLE
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = CHARACTER_TABLE)
data class CharacterSchema(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val status: CharacterStatus?,
    val species: String?,
    val type: String?,
    val gender: CharacterGender?,
    val origin: CharacterSchemaOrigin?,
    val location: CharacterSchemaLocation?,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String?,
    val isFavorite: Boolean = false
)

enum class CharacterStatus {
    @SerializedName("Alive")
    ALIVE,
    @SerializedName("Dead")
    DEAD,
    @SerializedName("Unknown", alternate = ["unknown"])
    UNKNOWN
}

enum class CharacterGender {
    @SerializedName("Female")
    FEMALE,
    @SerializedName("Male")
    MALE,
    @SerializedName("Genderless")
    GENDERLESS,
    @SerializedName("Unknown", alternate = ["unknown"])
    UNKNOWN
}

data class CharacterSchemaOrigin(val name: String?, val url: String?)

data class CharacterSchemaLocation(val name: String?, val url: String?)

class Converters {
    @TypeConverter
    fun originToJson(value: CharacterSchemaOrigin?): String? =
        value?.let { Gson().toJson(it).toString() }

    @TypeConverter
    fun jsonToOrigin(value: String?): CharacterSchemaOrigin? =
        value?.let { Gson().fromJson(it, CharacterSchemaOrigin::class.java) }

    @TypeConverter
    fun locationToJson(value: CharacterSchemaLocation?): String? =
        value?.let { Gson().toJson(it).toString() }

    @TypeConverter
    fun jsonToLocation(value: String?): CharacterSchemaLocation? =
        value?.let { Gson().fromJson(it, CharacterSchemaLocation::class.java) }

    @TypeConverter
    fun episodesToJson(value: List<String>?): String? =
        value?.let { Gson().toJson(it) }

    @TypeConverter
    fun jsonToEpisodes(value: String?): List<String>? =
        value?.let {
            val type = object : TypeToken<List<String>?>() {}.type
            Gson().fromJson(it, type)
        }
}
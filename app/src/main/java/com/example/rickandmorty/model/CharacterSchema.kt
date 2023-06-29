package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class CharacterSchema(
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

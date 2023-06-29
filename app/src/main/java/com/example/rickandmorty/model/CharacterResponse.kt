package com.example.rickandmorty.model

data class CharacterResponse(
    val info: CharacterResponseInfo?,
    val results: List<CharacterSchema>?
)

data class CharacterResponseInfo(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)

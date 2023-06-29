package com.example.rickandmorty.data.api

import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.CharacterSchema
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharacterResponse

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: Int): CharacterSchema

    @GET("character")
    suspend fun search(@Query("name") name: String): CharacterResponse
}
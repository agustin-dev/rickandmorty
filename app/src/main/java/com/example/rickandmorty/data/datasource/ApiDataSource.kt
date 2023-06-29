package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.data.api.CharactersApi
import com.example.rickandmorty.model.CharacterResponse
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val charactersApi: CharactersApi
) {
    suspend fun getCharacters(page: Int): CharacterResponse =
        charactersApi.getAllCharacters(page)
}
package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.data.api.CharactersApi
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val charactersApi: CharactersApi
) {
    suspend fun getCharacters(page: Int): CharacterResponse =
        charactersApi.getAllCharacters(page)

    suspend fun getSingleCharacter(id: Int): Flow<NetworkResult<CharacterSchema>> = flow {
        emit(NetworkResult.Loading())
        try {
            emit(NetworkResult.Success(charactersApi.getSingleCharacter(id)))
        } catch (exception: Exception) {
//            Log.e(ApiDataSource::class.toString(), "$exception")
            emit(NetworkResult.Error(exception.message.orEmpty()))
        }
    }

    fun search(name: String): Flow<NetworkResult<CharacterResponse?>> = flow {
        emit(NetworkResult.Loading())
        try {
            emit(NetworkResult.Success(charactersApi.search(name)))
        } catch (exception: Exception) {
//            Log.e(ApiDataSource::class.toString(), "$exception")
            emit(NetworkResult.Error(exception.message.orEmpty()))
        }
    }
}
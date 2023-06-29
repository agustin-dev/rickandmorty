package com.example.rickandmorty.data.repository

import android.util.Log
import com.example.rickandmorty.data.datasource.ApiDataSource
import com.example.rickandmorty.data.db.CharacterDao
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SingleCharacterRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val characterDao: CharacterDao
) {

    suspend fun getApiCharacter(id: Int): Flow<NetworkResult<CharacterSchema>> =
        apiDataSource.getSingleCharacter(id)

    fun getFavoriteCharacter(id: Int): Flow<NetworkResult<CharacterSchema>> = flow {
        emit(NetworkResult.Loading())
        try {
            emit(
                NetworkResult.Success(
                    characterDao.getCharacter(id).copy(isFavorite = true)
                )
            )
        } catch (exception: Exception) {
            emit(NetworkResult.Error(exception.message.orEmpty()))
        }
    }

    suspend fun addFavorite(characterSchema: CharacterSchema) {
        try {
            characterDao.addCharacter(characterSchema)
        } catch (exception: Exception) {
            Log.e(">>>>", "$exception")
        }
    }

    suspend fun deleteFavorite(characterSchema: CharacterSchema) {
        try {
            characterDao.deleteCharacter(characterSchema)
        } catch (exception: Exception) {
            Log.e(">>>>", "$exception")
        }
    }
}
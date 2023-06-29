package com.example.rickandmorty.domain

import com.example.rickandmorty.data.repository.SingleCharacterRepository
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.Screen
import com.example.rickandmorty.model.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val singleCharacterRepository: SingleCharacterRepository
) {
    suspend operator fun invoke(
        characterId: Int,
        source: Screen
    ): Flow<NetworkResult<CharacterSchema>> =
        when (source) {
            Screen.Search, Screen.List -> singleCharacterRepository.getApiCharacter(characterId)
            Screen.Favorites -> singleCharacterRepository.getFavoriteCharacter(characterId)
            else -> flowOf(NetworkResult.Error("Wrong source"))
        }
}
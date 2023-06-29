package com.example.rickandmorty.domain

import androidx.paging.PagingData
import com.example.rickandmorty.data.repository.CharactersRepository
import com.example.rickandmorty.model.CharacterSchema
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(): Flow<PagingData<CharacterSchema>> =
        charactersRepository.getFavoritesCharacters()
}
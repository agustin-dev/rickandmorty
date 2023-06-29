package com.example.rickandmorty.domain

import com.example.rickandmorty.data.repository.CharactersRepository
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(name: String): Flow<NetworkResult<CharacterResponse?>> =
        charactersRepository.search(name)
}
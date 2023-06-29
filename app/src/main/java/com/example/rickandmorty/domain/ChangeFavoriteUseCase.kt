package com.example.rickandmorty.domain

import com.example.rickandmorty.data.repository.SingleCharacterRepository
import com.example.rickandmorty.model.CharacterSchema
import javax.inject.Inject

class ChangeFavoriteUseCase @Inject constructor(
    private val singleCharacterRepository: SingleCharacterRepository
) {
    suspend operator fun invoke(characterSchema: CharacterSchema) {
        if (characterSchema.isFavorite) {
            singleCharacterRepository.deleteFavorite(characterSchema)
        } else {
            singleCharacterRepository.addFavorite(characterSchema)
        }
    }
}
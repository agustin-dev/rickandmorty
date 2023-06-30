package com.example.rickandmorty.domain

import com.example.rickandmorty.data.repository.SingleCharacterRepository
import com.example.rickandmorty.model.CharacterSchema
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ChangeFavoriteUseCaseTest {

    @RelaxedMockK
    private lateinit var singleCharacterRepository: SingleCharacterRepository

    private lateinit var changeFavoriteUseCase: ChangeFavoriteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        changeFavoriteUseCase = ChangeFavoriteUseCase(singleCharacterRepository)
    }

    @Test
    fun `invoke() when character is favorite`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(true)
        // When
        changeFavoriteUseCase(characterSchema)
        // Then
        coVerify { singleCharacterRepository.deleteFavorite(characterSchema) }
    }

    @Test
    fun `invoke() when character is not favorite`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(false)
        // When
        changeFavoriteUseCase(characterSchema)
        // Then
        coVerify { singleCharacterRepository.addFavorite(characterSchema) }
    }

}
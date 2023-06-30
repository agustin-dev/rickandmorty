package com.example.rickandmorty.domain

import com.example.rickandmorty.data.repository.SingleCharacterRepository
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult
import com.example.rickandmorty.model.Screen
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class GetCharacterUseCaseTest {
    @RelaxedMockK
    private lateinit var singleCharacterRepository: SingleCharacterRepository

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getCharacterUseCase = GetCharacterUseCase(singleCharacterRepository)
    }

    @Test
    fun `invoke() when source screen is Search`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(true)
        // When
        getCharacterUseCase(1, Screen.Search)
        // Then
        coVerify { singleCharacterRepository.getApiCharacter(1) }
    }

    @Test
    fun `invoke() when source screen is List`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(true)
        // When
        getCharacterUseCase(1, Screen.List)
        // Then
        coVerify { singleCharacterRepository.getApiCharacter(1) }
    }

    @Test
    fun `invoke() when source screen is Favorites`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(true)
        // When
        getCharacterUseCase(1, Screen.Favorites)
        // Then
        coVerify { singleCharacterRepository.getFavoriteCharacter(1) }
    }

    @Test
    fun `invoke() when source screen is Detail`() = runBlocking {
        // Given
        val characterSchema = mockk<CharacterSchema>()
        every { characterSchema.isFavorite }.returns(true)
        // When
        val result = getCharacterUseCase(1, Screen.Detail).toList()
        // Then
        assertTrue(result[0] is NetworkResult.Error)
        assertTrue(result[0].message == "Wrong source")
    }
}
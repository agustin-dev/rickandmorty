package com.example.rickandmorty.data.repository

import androidx.paging.testing.asSnapshot
import com.example.rickandmorty.data.datasource.ApiDataSource
import com.example.rickandmorty.data.datasource.ApiPagingSource
import com.example.rickandmorty.data.db.CharacterDao
import com.example.rickandmorty.data.db.DbPagingSource
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.CharacterResponseInfo
import com.example.rickandmorty.model.CharacterSchema
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


internal class CharactersRepositoryTest {

    @RelaxedMockK
    private lateinit var apiDataSource: ApiDataSource

    @RelaxedMockK
    private lateinit var characterDao: CharacterDao

    private lateinit var charactersRepository: CharactersRepository

    private val characterSchemas = (0 until 50).map { mockk<CharacterSchema>() }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        charactersRepository =
            CharactersRepository(
                ApiPagingSource(apiDataSource),
                DbPagingSource(characterDao),
                apiDataSource
            )
    }

    @Test
    fun `getApiCharacters() is successful`() = runBlocking {
        // Given
        mockApiDataSourceResponse()
        // When
        val result = charactersRepository.getApiCharacters().asSnapshot {
            scrollTo(1)
        }
        // Then
        assertEquals(
            characterSchemas.subList(0, 40),
            result
        )
    }

    @Test
    fun `getApiCharacters() is successful after scrolling next page`() = runBlocking {
        // Given
        mockApiDataSourceResponse()
        // When
        val result = charactersRepository.getApiCharacters().asSnapshot {
            scrollTo(30)
        }
        // Then
        assertEquals(
            characterSchemas,
            result
        )
    }

    @Test
    fun `getFavoritesCharacters() is successful`() = runBlocking {
        // Given
        mockCharacterDao()
        // When
        val result = charactersRepository.getFavoritesCharacters().asSnapshot {
            scrollTo(0)
        }
        // Then
        assertEquals(
            characterSchemas.subList(0, 40),
            result
        )
    }

    @Test
    fun `getFavoritesCharacters() is successful after scrolling next page`() = runBlocking {
        // Given
        mockCharacterDao()
        // When
        val result = charactersRepository.getFavoritesCharacters().asSnapshot {
            scrollTo(20)
        }
        // Then
        assertEquals(
            characterSchemas,
            result
        )
    }

    private fun mockApiDataSourceResponse() {
        coEvery { apiDataSource.getCharacters(1) }.returns(
            CharacterResponse(
                CharacterResponseInfo(50, 3, "2", null),
                characterSchemas.subList(0, 20)
            )
        )
        coEvery { apiDataSource.getCharacters(2) }.returns(
            CharacterResponse(
                CharacterResponseInfo(50, 3, "3", "1"),
                characterSchemas.subList(20, 40)
            )
        )
        coEvery { apiDataSource.getCharacters(3) }.returns(
            CharacterResponse(
                CharacterResponseInfo(50, 3, null, "2"),
                characterSchemas.subList(40, 50)
            )
        )
    }

    private fun mockCharacterDao() {
        coEvery { characterDao.getAllCharacters(0) }.returns(characterSchemas.subList(0, 20))
        coEvery { characterDao.getAllCharacters(20) }.returns(characterSchemas.subList(20, 40))
        coEvery { characterDao.getAllCharacters(40) }.returns(characterSchemas.subList(40, 50))
    }
}
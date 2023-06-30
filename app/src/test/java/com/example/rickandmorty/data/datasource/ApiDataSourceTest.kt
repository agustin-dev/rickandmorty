package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.data.api.CharactersApi
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.NetworkResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows


internal class ApiDataSourceTest {

    @RelaxedMockK
    private lateinit var charactersApi: CharactersApi

    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        apiDataSource = ApiDataSource(charactersApi)
    }

    @Test
    fun `getCharacters() is successful`() = runBlocking {
        // Given
        coEvery { charactersApi.getAllCharacters(1) }.returns(CharacterResponse(mockk(), mockk()))
        // When
        val result = apiDataSource.getCharacters(1)
        // Then
        assertNotNull(result)
    }

    @Test
    fun `getCharacters() fails`() {
        runBlocking {
            // Given
            coEvery { charactersApi.getAllCharacters(1) }.throws(Exception())
            // When - Then
            assertThrows<Exception> {
                apiDataSource.getCharacters(1)
            }
        }
    }

    @Test
    fun `getSingleCharacter() is successful`() = runBlocking {
        // Given
        coEvery { charactersApi.getSingleCharacter(1) }.returns(mockk())
        // When
        val result = apiDataSource.getSingleCharacter(1).toList()
        // Then
        assertTrue(result[0] is NetworkResult.Loading)
        assertTrue(result[1] is NetworkResult.Success)
    }

    @Test
    fun `getSingleCharacter() fails`() = runBlocking {
        // Given
        coEvery { charactersApi.getSingleCharacter(1) }.throws(Exception())
        // When
        val result = apiDataSource.getSingleCharacter(1).toList()
        // Then
        assertTrue(result[0] is NetworkResult.Loading)
        assertTrue(result[1] is NetworkResult.Error)
    }

    @Test
    fun `search() is successful`() = runBlocking {
        // Given
        coEvery { charactersApi.search(any()) }.returns(mockk())
        // When
        val result = apiDataSource.search("Rick").toList()
        // Then
        assertTrue(result[0] is NetworkResult.Loading)
        assertTrue(result[1] is NetworkResult.Success)
    }

    @Test
    fun `search() fails`() = runBlocking {
        // Given
        coEvery { charactersApi.search(any()) }.throws(Exception())
        // When
        val result = apiDataSource.search("Rick").toList()
        // Then
        assertTrue(result[0] is NetworkResult.Loading)
        assertTrue(result[1] is NetworkResult.Error)
    }
}
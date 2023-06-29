package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.datasource.ApiDataSource
import com.example.rickandmorty.data.datasource.ApiPagingSource
import com.example.rickandmorty.data.db.DbPagingSource
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiPagingSource: ApiPagingSource,
    private val dbPagingSource: DbPagingSource,
    private val dataSource: ApiDataSource
) {

    fun getApiCharacters(): Flow<PagingData<CharacterSchema>> =
        Pager(PagingConfig(pageSize = 20)) {
            apiPagingSource
        }.flow

    fun getFavoritesCharacters(): Flow<PagingData<CharacterSchema>> =
        Pager(PagingConfig(pageSize = 20)) {
            dbPagingSource
        }.flow

    fun search(name: String): Flow<NetworkResult<CharacterResponse?>> =
        dataSource.search(name)
}
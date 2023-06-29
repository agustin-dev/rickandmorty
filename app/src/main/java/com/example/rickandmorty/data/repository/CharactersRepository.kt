package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.datasource.ApiPagingSource
import com.example.rickandmorty.data.db.DbPagingSource
import com.example.rickandmorty.model.CharacterSchema
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiPagingSource: ApiPagingSource,
    private val dbPagingSource: DbPagingSource
) {

    fun getApiCharacters(): Flow<PagingData<CharacterSchema>> =
        Pager(PagingConfig(pageSize = 20)) {
            apiPagingSource
        }.flow

    fun getFavoritesCharacters(): Flow<PagingData<CharacterSchema>> =
        Pager(PagingConfig(pageSize = 20)) {
            dbPagingSource
        }.flow
}
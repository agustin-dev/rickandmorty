package com.example.rickandmorty.data.db

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.model.CharacterSchema
import javax.inject.Inject

class DbPagingSource @Inject constructor(
    private val dao: CharacterDao
) : PagingSource<Int, CharacterSchema>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterSchema>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterSchema> =
        try {
            val nextPage = params.key ?: 0
            val characters = dao.getAllCharacters(nextPage * 20)
            if (characters.isEmpty()) {
                throw Exception()
            }
            LoadResult.Page(
                data = characters,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (characters.isEmpty()) null else nextPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
}
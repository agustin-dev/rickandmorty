package com.example.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.model.CharacterSchema
import java.lang.Exception
import javax.inject.Inject

class ApiPagingSource @Inject constructor(
    private val dataSource: ApiDataSource
) : PagingSource<Int, CharacterSchema>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterSchema>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterSchema> =
        try {
            val nextPage = params.key ?: 1
            val charactersResponse = dataSource.getCharacters(nextPage)
            val totalPages = charactersResponse.info?.pages
            LoadResult.Page(
                data = charactersResponse.results.orEmpty(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (totalPages == null || totalPages == nextPage) null else nextPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
}
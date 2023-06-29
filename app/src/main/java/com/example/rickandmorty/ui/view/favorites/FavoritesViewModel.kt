package com.example.rickandmorty.ui.view.favorites

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.rickandmorty.domain.GetFavoritesCharactersUseCase
import com.example.rickandmorty.model.CharacterSchema
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesCharactersUseCase: GetFavoritesCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<CharacterSchema>> =
        getFavoritesCharactersUseCase()
            .flowOn(Dispatchers.IO)
}

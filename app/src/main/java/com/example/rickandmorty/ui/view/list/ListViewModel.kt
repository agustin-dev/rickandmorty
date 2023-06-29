package com.example.rickandmorty.ui.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.domain.GetApiCharactersUseCase
import com.example.rickandmorty.model.CharacterSchema
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getApiCharactersUseCase: GetApiCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<CharacterSchema>> =
        getApiCharactersUseCase()
            .cachedIn(viewModelScope)
}

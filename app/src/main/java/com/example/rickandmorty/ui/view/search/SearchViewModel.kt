package com.example.rickandmorty.ui.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.SearchUseCase
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchUseCase: SearchUseCase
) : ViewModel() {
    val result = MutableStateFlow<NetworkResult<CharacterResponse?>>(NetworkResult.Idle())
    val text = MutableStateFlow("")

    init {
        text
            .debounce(300)
            .filterNot { it.isEmpty() }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .flatMapLatest { searchUseCase(it) }
            .onEach { result.value = it }
            .shareIn(viewModelScope, SharingStarted.Lazily, 20)
            .launchIn(viewModelScope)
    }

    fun onValueChange(input: String) {
        text.value = input
    }
}
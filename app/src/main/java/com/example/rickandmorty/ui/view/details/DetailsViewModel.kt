package com.example.rickandmorty.ui.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.ChangeFavoriteUseCase
import com.example.rickandmorty.domain.GetCharacterUseCase
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.Screen
import com.example.rickandmorty.model.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
) : ViewModel() {

    private val _characterDetails = MutableStateFlow<NetworkResult<CharacterSchema>>(
        NetworkResult.Idle()
    )
    val characterDetails = _characterDetails.asStateFlow()

    fun getCharacter(characterId: Int, source: Screen) {
        viewModelScope.launch {
            getCharacterUseCase(characterId, Screen.Favorites).collect { favorite ->
                when {
                    source == Screen.Favorites || favorite is NetworkResult.Success ->
                        _characterDetails.value = favorite

                    else ->
                        getCharacterUseCase(characterId, source).collect {
                            _characterDetails.value = it
                        }
                }
            }
        }
    }

    fun switchFavorite() {
        viewModelScope.launch {
            val characterDetails = characterDetails.value
            if (characterDetails is NetworkResult.Success) {
                characterDetails.data?.let {
                    changeFavoriteUseCase(it)
                }
            }
        }
    }
}
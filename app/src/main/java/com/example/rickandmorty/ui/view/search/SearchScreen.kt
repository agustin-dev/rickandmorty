package com.example.rickandmorty.ui.view.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult
import com.example.rickandmorty.ui.view.card.CharacterCard

@Composable
fun SearchScreen(
    vm: SearchViewModel,
    paddingValues: PaddingValues,
    onCardClicked: (CharacterSchema) -> Unit
) {
    val result by vm.result.collectAsState()
    val text = vm.text.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = text.value,
            onValueChange = vm::onValueChange,
            label = { Text(text = stringResource(R.string.character_name), modifier = Modifier.fillMaxWidth()) },
            placeholder = { Text(text = stringResource(R.string.character)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Idle -> {
                    Text(text = stringResource(R.string.search_for_a_character))
                }

                is NetworkResult.Success -> {
                    result.data?.let {
                        ShowCharactersList(it, onCardClicked)
                    }
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = stringResource(R.string.no_characters_found))
                }
            }
        }
    }
}

@Composable
fun ShowCharactersList(
    characterResponse: CharacterResponse,
    onCardClicked: (CharacterSchema) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(characterResponse.results?.size ?: 0) { index ->
            val character = characterResponse.results?.get(index)
            character?.let {
                CharacterCard(it, MaterialTheme.colorScheme.onPrimary, onCardClicked)
            }
        }
    }
}

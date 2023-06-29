package com.example.rickandmorty.ui.view.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.R
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.ui.view.card.SmallCharacterCard

@Composable
fun ListScreen(
    vm: ListViewModel,
    paddingValues: PaddingValues,
    onCardClicked: (character: CharacterSchema) -> Unit
) {
    val characters = vm.characters.collectAsLazyPagingItems()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (characters.loadState.refresh) {
            is LoadState.Error ->
                Text(
                    text = stringResource(R.string.check_your_internet_connection),
                    textAlign = TextAlign.Center,
                )
            is LoadState.Loading ->
                CircularProgressIndicator()
            is LoadState.NotLoading ->
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 4.dp,
                            top = 4.dp,
                            end = 4.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                    columns = GridCells.Fixed(3)
                ) {
                    items(characters.itemCount) { index ->
                        val character = characters[index]
                        character?.let {
                            SmallCharacterCard(character = it, onCardClicked = onCardClicked)
                        }
                    }
                }
        }
    }
}
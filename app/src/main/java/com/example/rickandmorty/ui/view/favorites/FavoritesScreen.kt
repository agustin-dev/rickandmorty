package com.example.rickandmorty.ui.view.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.rickandmorty.R
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.ui.theme.favoriteContainer
import com.example.rickandmorty.ui.view.card.CharacterCard

@Composable
fun FavoritesScreen(
    vm: FavoritesViewModel,
    paddingValues: PaddingValues,
    onCardClicked: (CharacterSchema) -> Unit
) {
    val characters = vm.characters.collectAsLazyPagingItems()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (characters.loadState.refresh) {
            is LoadState.Error ->
                Text(
                    text = stringResource(R.string.no_favortites_message),
                    textAlign = TextAlign.Center,
                )
            is LoadState.Loading ->
                CircularProgressIndicator()
            is LoadState.NotLoading ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 4.dp,
                            top = 4.dp,
                            end = 4.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    items(
                        count = characters.itemCount,
                        key = characters.itemKey(),
                        contentType = characters.itemContentType(
                        )
                    ) { index ->
                        val item = characters[index]
                        item?.let {
                            CharacterCard(it, MaterialTheme.colorScheme.favoriteContainer, onCardClicked)
                        }
                    }
                }
        }
    }
}
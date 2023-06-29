package com.example.rickandmorty.ui.view.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmorty.R
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.NetworkResult

@Composable
fun DetailsScreen(
    vm: DetailsViewModel,
    paddingValues: PaddingValues,
    isFavorite: MutableState<Boolean>,
    topBarTitle: MutableState<String>
) {
    val characterDetails by vm.characterDetails.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val details = characterDetails) {
            is NetworkResult.Error -> {
                topBarTitle.value = stringResource(R.string.error)
                Text(
                    details.message ?: stringResource(R.string.error),
                    modifier = Modifier.padding(8.dp)
                )
            }

            is NetworkResult.Success -> {
                details.data?.let {
                    topBarTitle.value = it.name.orEmpty()
                    isFavorite.value = it.isFavorite
                    SuccessScreen(
                        characterSchema = it,
                        paddingValues = paddingValues
                    )
                }
            }

            else -> {
                topBarTitle.value = stringResource(R.string.loading)
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
internal fun DetailRow(@StringRes key: Int, value: String?) {
    Divider()
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(key),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = value.takeIf { it.isNullOrEmpty().not() } ?: "-",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(0.7f)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SuccessScreen(characterSchema: CharacterSchema, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = paddingValues.calculateTopPadding()
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GlideImage(
                model = characterSchema.image,
                contentDescription = null,
                modifier = Modifier
                    .size(260.dp)
                    .padding(12.dp),
                alignment = Alignment.TopCenter
            )
        }
        Text(
            text = characterSchema.name.orEmpty(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        DetailRow(key = R.string.status, value = characterSchema.status?.name?.lowercase()?.capitalize(LocaleList.current))
        DetailRow(key = R.string.species, value = characterSchema.species)
        DetailRow(key = R.string.type, value = characterSchema.type)
        DetailRow(key = R.string.gender, value = characterSchema.gender?.name?.lowercase()?.capitalize(LocaleList.current))
        DetailRow(key = R.string.origin, value = characterSchema.origin?.name.orEmpty())
        DetailRow(key = R.string.location, value = characterSchema.location?.name.orEmpty())
        DetailRow(key = R.string.episodes,
            value = characterSchema.episode?.joinToString(", ") { it.substringAfterLast("/") }
        )
    }
}

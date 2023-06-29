package com.example.rickandmorty.ui.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmorty.R
import com.example.rickandmorty.model.CharacterSchema

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterCard(
    character: CharacterSchema,
    cardColor: Color,
    onCardClicked: (character: CharacterSchema) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
            .clickable { onCardClicked(character) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clip(CircleShape)
                    .fillMaxWidth(0.3f)
            )
            Column {
                Text(
                    text = character.name.orEmpty(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.status).plus(": ")
                        .plus(character.status?.name?.lowercase()?.capitalize(LocaleList.current))
                )
                Text(
                    text = stringResource(R.string.species).plus(": ")
                        .plus(character.species.orEmpty())
                )
                Text(
                    text = stringResource(R.string.gender).plus(": ")
                        .plus(character.gender?.name?.lowercase()?.capitalize(LocaleList.current))
                )
            }
        }
    }
}

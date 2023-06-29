package com.example.rickandmorty.ui.view.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmorty.model.CharacterSchema

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun SmallCharacterCard(
    character: CharacterSchema,
    onCardClicked: (character: CharacterSchema) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(all = 4.dp)
            .clickable { onCardClicked(character) },
        shape = RectangleShape,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
            )
            Text(text = character.name.orEmpty(), modifier = Modifier.basicMarquee())
        }
    }
}

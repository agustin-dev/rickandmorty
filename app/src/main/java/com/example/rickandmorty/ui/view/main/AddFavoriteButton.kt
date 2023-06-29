package com.example.rickandmorty.ui.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmorty.model.Screen
import com.example.rickandmorty.ui.theme.favorite

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddFavoriteButton(
    navHostController: NavHostController,
    isFavorite: MutableState<Boolean>,
    onClick: (() -> Unit)?
) {
    val currentBackStack by navHostController.currentBackStackEntryAsState()
    AnimatedVisibility(
        visible = onClick != null && currentBackStack?.destination?.route == Screen.Detail.route,
        enter = scaleIn(animationSpec = tween(delayMillis = 500)),
        exit = scaleOut()
    ) {
        FloatingActionButton(
            onClick = onClick ?: { },
            shape = CircleShape,
            containerColor = if (isFavorite.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary,
            contentColor = if (isFavorite.value) MaterialTheme.colorScheme.favorite else LocalContentColor.current
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
            )
        }
    }
}
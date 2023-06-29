package com.example.rickandmorty.ui.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmorty.model.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailsTopBar(navHostController: NavHostController, title: MutableState<String>) {
    val currentBackStack by navHostController.currentBackStackEntryAsState()
    AnimatedVisibility(
        visible = currentBackStack?.destination?.route == Screen.Detail.route,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        TopAppBar(
            title = { Text(text = title.value, modifier = Modifier.basicMarquee()) },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )
    }
}
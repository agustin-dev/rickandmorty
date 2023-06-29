package com.example.rickandmorty.ui.view.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmorty.model.Screen

@Composable
fun CharactersBottomNav(navHostController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry = navHostController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        NavigationBarItem(
            selected = currentDestination?.route == Screen.Search.route,
            onClick = {
                navHostController.navigate(Screen.Search.route) {
                    popUpTo(Screen.List.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            label = { Text(text = Screen.Search.route.uppercase()) }
        )

        NavigationBarItem(
            selected = currentDestination?.route == Screen.List.route,
            onClick = {
                navHostController.navigate(Screen.List.route) {
                    popUpTo(Screen.List.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(imageVector = Icons.Filled.DateRange, contentDescription = null) },
            label = { Text(text = Screen.List.route.uppercase()) }
        )

        NavigationBarItem(
            selected = currentDestination?.route == Screen.Favorites.route,
            onClick = {
                navHostController.navigate(Screen.Favorites.route) {
                    popUpTo(Screen.Favorites.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = null) },
            label = { Text(text = Screen.Favorites.route.uppercase()) }
        )
    }
}
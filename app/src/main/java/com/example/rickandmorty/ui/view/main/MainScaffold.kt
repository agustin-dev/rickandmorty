package com.example.rickandmorty.ui.view.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.model.Screen
import com.example.rickandmorty.ui.view.details.DetailsScreen
import com.example.rickandmorty.ui.view.details.DetailsViewModel
import com.example.rickandmorty.ui.view.favorites.FavoritesScreen
import com.example.rickandmorty.ui.view.favorites.FavoritesViewModel
import com.example.rickandmorty.ui.view.list.ListScreen
import com.example.rickandmorty.ui.view.list.ListViewModel
import com.example.rickandmorty.ui.view.search.SearchScreen
import com.example.rickandmorty.ui.view.search.SearchViewModel

@Composable
fun MainScaffold(navController: NavHostController) {
    Scaffold(
        bottomBar = { CharactersBottomNav(navController) },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route
        ) {
            composable(Screen.Search.route) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                SearchScreen(searchViewModel, paddingValues)
            }
            composable(Screen.List.route) {
                val listViewModel: ListViewModel = hiltViewModel()
                ListScreen(listViewModel, paddingValues) { character ->
                    navController.navigate(Screen.Detail.createRoute(character.id, Screen.List))
                }
            }
            composable(Screen.Favorites.route) {
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()
                FavoritesScreen(favoritesViewModel, paddingValues)
            }
            composable(Screen.Detail.route) {
                val detailsViewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(detailsViewModel, paddingValues)
            }
        }
    }
}

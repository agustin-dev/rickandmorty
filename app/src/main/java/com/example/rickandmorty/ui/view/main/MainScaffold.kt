package com.example.rickandmorty.ui.view.main

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.model.Constants
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
    val topBarTitle = rememberSaveable { mutableStateOf("") }
    val isFavorite = rememberSaveable { mutableStateOf<Boolean>(false) }
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }
    Scaffold(
        topBar = { DetailsTopBar(navController, topBarTitle) },
        bottomBar = { CharactersBottomNav(navController) },
        floatingActionButton = { AddFavoriteButton(navController, isFavorite, fabOnClick) },
        floatingActionButtonPosition = FabPosition.End,
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
                FavoritesScreen(favoritesViewModel, paddingValues) { character ->
                    navController.navigate(Screen.Detail.createRoute(character.id, Screen.Favorites))
                }
            }
            composable(Screen.Detail.route) {
                it.arguments?.getString(Constants.CHARACTER_ID)?.let { characterId ->
                    Screen.Detail.getSource(it.arguments?.getString(Constants.SCREEN_SOURCE))?.let { sourceScreen ->
                        val detailsViewModel: DetailsViewModel = hiltViewModel()
                        LaunchedEffect(Unit) {
                            detailsViewModel.getCharacter(characterId.toInt(), sourceScreen)
                            setFabOnClick {
                                isFavorite.value = !isFavorite.value
                                detailsViewModel.switchFavorite()
                            }
                        }
                        DetailsScreen(detailsViewModel, paddingValues, isFavorite, topBarTitle)
                    }
                }
            }
        }
    }
}

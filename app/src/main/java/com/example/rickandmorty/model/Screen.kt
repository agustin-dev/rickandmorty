package com.example.rickandmorty.model

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object List : Screen("list")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{${Constants.SCREEN_SOURCE}}/{${Constants.CHARACTER_ID}}") {
        fun createRoute(characterId: Int?, source: Screen) = "detail/${source.route}/$characterId"
    }
}
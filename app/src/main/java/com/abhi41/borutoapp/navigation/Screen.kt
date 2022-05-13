package com.abhi41.borutoapp.navigation

sealed class Screen(val route: String){
    object Splash:Screen("splash_screen")
    object Welcome:Screen("welcome_screen")
    object Home:Screen("home_screen")
    object DetailsScreen:Screen("detail_screen/{heroId}"){ //we use required arguments
        fun passHeroId(heroId: Int): String{
            return "details_screen/$heroId"
        }
    }
    object SearchScreen: Screen("search_screen")
}

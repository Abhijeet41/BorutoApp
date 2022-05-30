package com.abhi41.borutoapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.abhi41.borutoapp.presentation.screen.details.DetailsScreen
import com.abhi41.borutoapp.presentation.screen.home.HomeScreen
import com.abhi41.borutoapp.presentation.screen.search.SearchScreen
import com.abhi41.borutoapp.presentation.screen.splash.SplashScreen
import com.abhi41.borutoapp.presentation.screen.welcome.WelcomeScreen
import com.abhi41.borutoapp.util.Constants.DETAILS_ARGUMENTS_KEY
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen( navHostController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController)
        }
        composable(
            route = Screen.DetailsScreen.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENTS_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navController = navController)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navHostController = navController)
        }

    }
}
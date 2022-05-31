package com.abhi41.borutoapp.presentation.screen.home


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.abhi41.borutoapp.navigation.Screen
import com.abhi41.borutoapp.presentation.common.ListContent
import com.abhi41.borutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    //change status bar color by applying color palette
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    Scaffold(topBar = {
        HomeTopBar(onSearchClicked = {
            navHostController.navigate(Screen.SearchScreen.route)
        })
    }, content = {
        ListContent(
            heros = allHeroes,
            navHostController = navHostController
        )
    })
}
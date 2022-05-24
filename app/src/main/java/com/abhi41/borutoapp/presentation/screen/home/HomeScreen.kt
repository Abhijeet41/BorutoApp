package com.abhi41.borutoapp.presentation.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.abhi41.borutoapp.navigation.Screen
import com.abhi41.borutoapp.presentation.common.ListContent
import com.abhi41.borutoapp.presentation.screen.components.RatingWidget
import com.abhi41.borutoapp.ui.theme.LARGE_PADDING

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

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
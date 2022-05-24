package com.abhi41.borutoapp.presentation.screen.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.abhi41.borutoapp.presentation.common.ListContent

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchedClicked = {
                    searchViewModel.searchHeroes(query = it)
                },
                onClosedClicked = {
                    navHostController.popBackStack()
                }
            )
        },
        content = {
            ListContent(heros = heroes, navHostController = navHostController)
        }
    )
}
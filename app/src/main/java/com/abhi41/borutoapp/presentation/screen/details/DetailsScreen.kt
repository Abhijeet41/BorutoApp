package com.abhi41.borutoapp.presentation.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    /*
      Note: Passing the hole object to DetailScreen is not a good practice instead of that we should pass
      primitive value int,boolean etc in this case we pass only id and with the help of
      Id we can fetch data from database
     */

   // val selectedHero = detailsViewModel.selectedHero
    val selectedHero by detailsViewModel.selectedHero.collectAsState()



    DetailsContent(navHostController = navController, selectedHero = selectedHero)
}
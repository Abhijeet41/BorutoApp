package com.abhi41.borutoapp.presentation.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abhi41.borutoapp.util.Constants
import com.abhi41.borutoapp.util.Constants.BASE_URL
import com.abhi41.borutoapp.util.PaletteGenerator
import kotlinx.coroutines.flow.collectLatest

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
    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navHostController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPalette()
    }

    val context  = LocalContext.current
    LaunchedEffect(key1 = true) {
        /* this means block inside this launch effect will trigger only first time we called our
        DetailsScreen */
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                   val bitmap = PaletteGenerator.convertImageToBitmap(
                       imageUrl = "$BASE_URL${selectedHero?.image}",
                       context = context
                   )

                    if (bitmap != null){
                        detailsViewModel.setColorPalette(
                            colors = PaletteGenerator.extractColorsFromBitmap(bitmap)
                        )
                    }
                }
            }
        }
    }

}
package com.abhi41.borutoapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.abhi41.borutoapp.R
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.navigation.Screen
import com.abhi41.borutoapp.presentation.screen.components.RatingWidget
import com.abhi41.borutoapp.ui.theme.*
import com.abhi41.borutoapp.util.Constants.BASE_URL

private const val TAG = "ListContent"

//so we have to display same list for search screen and our home screen
@ExperimentalCoilApi
@Composable
fun ListContent(
    heros: LazyPagingItems<Hero>,
    navHostController: NavHostController
) {
    Log.d(TAG, heros.loadState.toString())
    val result = handlePagingResult(heros = heros)
    if (result){
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING) //its like space between item list
        ) {
            items(
                items = heros,
                key = { hero ->
                    hero.id
                }
            ) { hero ->
                hero?.let {
                    HeroItem(hero = it, navHostController = navHostController)
                }
            }
        }
    }


}

@Composable
fun handlePagingResult(
    heros: LazyPagingItems<Hero>
): Boolean {
    heros.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when{
            loadState.refresh is LoadState.Loading ->{
                ShimmerEffect()
                false
            }
            error != null-> {
                Log.d(TAG, "EmptyScreen: ${error.toString()}")
                EmptyScreen(error = error, heroes = heros)
                false
            }
            heros.itemCount == 0 ->{
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}


@ExperimentalCoilApi
@Composable
fun HeroItem(
    hero: Hero,
    navHostController: NavHostController
) {
    val painter = rememberImagePainter(data = "$BASE_URL${hero.image}") {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navHostController.navigate(Screen.DetailsScreen.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart //black over should be at the start of the bottom
    ) {
        Surface(
            shape = RoundedCornerShape(
                size = LARGE_PADDING
            )
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis //show 3 dots if text size is huge
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis //show 3 dots if text size is huge
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Suske",
            image = "",
            about = "Some random text...",
            rating = 3.5,
            power = 100,
            month = "",
            day = "",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList()
        ), navHostController = rememberNavController()
    )
}
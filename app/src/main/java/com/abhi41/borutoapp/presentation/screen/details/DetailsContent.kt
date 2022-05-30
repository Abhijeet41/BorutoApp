package com.abhi41.borutoapp.presentation.screen.details

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.abhi41.borutoapp.R
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.presentation.screen.components.InfoBox
import com.abhi41.borutoapp.presentation.screen.components.OrderedList
import com.abhi41.borutoapp.ui.theme.*
import com.abhi41.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINE
import com.abhi41.borutoapp.util.Constants.BASE_URL
import com.abhi41.borutoapp.util.Constants.MIN_BACKGROUND_IMAGE_HEIGHT

private const val TAG = "DetailsContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navHostController: NavHostController,
    selectedHero: Hero?
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    val currentSheetFraction = scaffoldState.currentSheetFraction
    Log.d(TAG, "Fraction NEW: $currentSheetFraction")
    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd =radiusAnim
        ),
        scaffoldState = scaffoldState, //bottomsheet default state is expanded
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            if (selectedHero != null) {
                BottomSheetContent(selectedHero = selectedHero)
            }
        },
        content = {

            if (selectedHero != null) {
                BackGroundContent(
                    heroImage = selectedHero.image,
                    imageFraction = currentSheetFraction,
                    onCloseClicked = {
                        navHostController.popBackStack()
                    }
                )
            }

        }
    )
}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(id = R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.month}",
                smallText = stringResource(id = R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.day}",
                smallText = stringResource(id = R.string.birthday),
                textColor = contentColor
            )
        }
        //About section
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SMALL_PADDING),
            text = stringResource(id = R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINE
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(id = R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(id = R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(id = R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )

        }
    }
}

@Composable
fun BackGroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${heroImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

//extension function
@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        Log.d(TAG, "Fraction: $fraction")
        Log.d(TAG, "Fraction Target: $targetValue")
        Log.d(TAG, "Fraction Current: $currentValue")

        return when {
            currentValue == BottomSheetValue.Collapsed &&
                    targetValue == BottomSheetValue.Collapsed -> 1f

            currentValue == BottomSheetValue.Expanded &&
                    targetValue == BottomSheetValue.Expanded -> 0f

            //this means our bottomsheet is collapsed and moving to expanded state
            currentValue == BottomSheetValue.Collapsed &&
                    targetValue == BottomSheetValue.Expanded -> 1f - fraction

            currentValue == BottomSheetValue.Expanded &&
                    targetValue == BottomSheetValue.Collapsed -> 0f + fraction

            else -> {
                fraction
            }
        }
    }


@Preview
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            name = "Naruto",
            image = "",
            about = stringResource(id = R.string.lorem),
            rating = 4.5,
            power = 0,
            month = "Oct",
            day = "1st",
            family = listOf("Minto", "Kushina", "Boruto", "Himawari"),
            abilities = listOf("Sage Mode", "Shadow Clone", "Resengan"),
            natureTypes = listOf("Earth", "Wind")
        )
    )
}
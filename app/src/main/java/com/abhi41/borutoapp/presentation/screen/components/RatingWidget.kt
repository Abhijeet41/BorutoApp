package com.abhi41.borutoapp.presentation.screen.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhi41.borutoapp.R
import com.abhi41.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.abhi41.borutoapp.ui.theme.LightGray
import com.abhi41.borutoapp.ui.theme.StarColor

private const val TAG = "RatingWidget"

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val result = calculateStars(rating = rating)

    val startPathString = stringResource(id = R.string.star_path)
    val starPath: Path = remember {
        PathParser().parsePathString(pathData = startPathString).toPath()
    }
    val startPathBounds: Rect = remember {
        starPath.getBounds()
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it){
                FilledStar(
                    startPath = starPath,
                    startPathBounds = startPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["halfFilledStars"]?.let {
           repeat(it){
               HalfFilledStar(
                   startPath = starPath,
                   startPathBounds = startPathBounds,
                   scaleFactor = scaleFactor
               )
           }
        }
        result["emptyStars"]?.let {
            repeat(it){
                EmptyStar(
                    startPath = starPath,
                    startPathBounds = startPathBounds,
                    scaleFactor = scaleFactor
                )
            }

        }
    }
}


@Composable
fun FilledStar(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = this.size

        scale(scale = scaleFactor) {
            val pathWidth = startPathBounds.width
            val pathHeight = startPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = startPath,
                    color = StarColor,
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = startPathBounds.width
            val pathHeight = startPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = startPath,
                    color = LightGray.copy(alpha = 0.5f),
                )
                clipPath(path = startPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = startPathBounds.maxDimension / 1.7f,
                            height = startPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = startPathBounds.width
            val pathHeight = startPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = startPath,
                    color = LightGray.copy(alpha = 0.5f),
                )

            }
        }
    }
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    //we don't nee more than 5 star
    val maxStars by remember { mutableStateOf(5) }

    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    //so by using launched effect we don't need to create scope manually

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".") //to recive two int value we need map
            .map { it.toInt() }
        //if we get rating vale 4.5 then  first number value is 4 and last number value is 5
        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber

            if (lastNumber in 1..5) { //caclulate half filled star
                halfFilledStars++
            }
            if (lastNumber in 6..9) { //calculate filled star
                filledStars++
            }

            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d(TAG, "Invalid Rating Number: ")
        }

    }
    emptyStars = maxStars - (filledStars + halfFilledStars) //empty star calculation
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
}


@Composable
@Preview(showBackground = true)
fun FilledStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath: Path = remember {
        PathParser().parsePathString(pathData = startPathString).toPath()
    }
    val startPathBounds: Rect = remember {
        starPath.getBounds()
    }
    FilledStar(
        startPath = starPath,
        startPathBounds = startPathBounds,
        scaleFactor = 2f
    )
}

@Composable
@Preview(showBackground = true)
fun HalfFilledStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath: Path = remember {
        PathParser().parsePathString(pathData = startPathString).toPath()
    }
    val startPathBounds: Rect = remember {
        starPath.getBounds()
    }
    HalfFilledStar(
        startPath = starPath,
        startPathBounds = startPathBounds,
        scaleFactor = 2f
    )
}

@Composable
@Preview(showBackground = true)
fun EmptyStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath: Path = remember {
        PathParser().parsePathString(pathData = startPathString).toPath()
    }
    val startPathBounds: Rect = remember {
        starPath.getBounds()
    }
    EmptyStar(
        startPath = starPath,
        startPathBounds = startPathBounds,
        scaleFactor = 2f
    )
}
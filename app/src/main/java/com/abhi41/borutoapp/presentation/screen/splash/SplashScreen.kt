package com.abhi41.borutoapp.presentation.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.abhi41.borutoapp.R
import com.abhi41.borutoapp.ui.theme.Purple500
import com.abhi41.borutoapp.ui.theme.Purple700
import  androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.borutoapp.navigation.Screen

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    //val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState() we can also write like this
    val onBoardingCompleted = splashViewModel.onBoardingCompleted.collectAsState()
    val degrees = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navHostController.popBackStack() //this means remove splash screen from back stack
        if (onBoardingCompleted.value){ //user clicked finish button
            navHostController.navigate(Screen.Home.route)
        }else{
            navHostController.navigate(Screen.Welcome.route)
        }
    }
    Splash(degrees = degrees.value)
}

@Composable
fun Splash(degrees: Float) {
    if (isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees = degrees),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(R.string.app_logo)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(Purple700, Purple500)
                    ),
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees = degrees),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(R.string.app_logo)
            )
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(degrees = 0f)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenDarkPreview() {
    Splash(0f)
}
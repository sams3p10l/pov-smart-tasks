package com.example.smarttasks.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttasks.R
import com.example.smarttasks.ui.theme.LogoOffset
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.Yellow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    var showFirst by remember { mutableStateOf(false) }
    var showSecond by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showFirst = true
        delay(ANIMATION_DURATION.toLong())
        showSecond = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow) //todo look into why MaterialTheme.background is not working
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = LogoOffset),
            visible = showFirst,
            enter = fadeIn(animationSpec = tween(ANIMATION_DURATION))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App logo",
                contentScale = FixedScale(IMAGE_SCALE_FACTOR)
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = showSecond,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(ANIMATION_DURATION)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.intro_illustration),
                contentDescription = "Illustration",
                contentScale = FixedScale(IMAGE_SCALE_FACTOR)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SmartTasksTheme {
        SplashScreen()
    }
}

private const val IMAGE_SCALE_FACTOR = 1.2f
private const val ANIMATION_DURATION = 1000
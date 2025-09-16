package com.example.smarttasks.ui.composable

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarttasks.R
import com.example.smarttasks.ui.theme.LogoOffset
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.Yellow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    val splashState = remember { MutableTransitionState(AnimationPhase.AllHidden) }
    val transition = updateTransition(splashState)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow) //todo look into why MaterialTheme.background is not working
    ) {
        val firstAnimation by transition.animateFloat(
            transitionSpec = { tween(ANIMATION_DURATION) }
        ) { state ->
            if (state >= AnimationPhase.FirstVisible) 1f else 0f
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App logo",
            contentScale = FixedScale(IMAGE_SCALE_FACTOR),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = LogoOffset)
                .alpha(firstAnimation)
        )

        val secondAnimation by transition.animateDp(
            transitionSpec = { tween(ANIMATION_DURATION) }
        ) { state ->
            if (state == AnimationPhase.SecondVisible) ILLUSTRATION_ANIMATION_START_DP else ILLUSTRATION_ANIMATION_END_DP
        }
        Image(
            painter = painterResource(id = R.drawable.intro_illustration),
            contentDescription = "Illustration",
            contentScale = FixedScale(IMAGE_SCALE_FACTOR),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = secondAnimation)
        )
    }

    LaunchedEffect(transition.currentState) {
        when (transition.currentState) {
            AnimationPhase.AllHidden -> {
                splashState.targetState = AnimationPhase.FirstVisible
            }

            AnimationPhase.FirstVisible -> {
                splashState.targetState = AnimationPhase.SecondVisible
            }

            AnimationPhase.SecondVisible -> {
                delay(ANIMATION_END_DELAY) //for animation smoothness
                onFinished()
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SmartTasksTheme {
        SplashScreen(
            onFinished = {}
        )
    }
}

private enum class AnimationPhase {
    AllHidden,
    FirstVisible,
    SecondVisible
}

private const val IMAGE_SCALE_FACTOR = 1.2f
private const val ANIMATION_DURATION = 700
private const val ANIMATION_END_DELAY = 300L
private val ILLUSTRATION_ANIMATION_START_DP = 0.dp
private val ILLUSTRATION_ANIMATION_END_DP = 400.dp
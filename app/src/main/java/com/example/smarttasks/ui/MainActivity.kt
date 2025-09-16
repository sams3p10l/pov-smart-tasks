package com.example.smarttasks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.smarttasks.ui.composable.SplashScreen
import com.example.smarttasks.ui.composable.TasksScreen
import com.example.smarttasks.ui.model.Screen
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartTasksTheme {
                val screen by mainViewModel.currentScreen.collectAsState()

                AnimatedContent(
                    targetState = screen,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(SCREEN_ANIMATION_DURATION_IN)) togetherWith fadeOut(
                            animationSpec = tween(SCREEN_ANIMATION_DURATION_OUT)
                        )
                    }
                ) { screen ->
                    when (screen) {
                        Screen.Splash -> SplashScreen(
                            onFinished = {
                                mainViewModel.navigateTo(Screen.Tasks)
                            }
                        )
                        Screen.Tasks -> TasksScreen()
                        else -> Unit
                    }
                }
            }
        }
    }
}

private const val SCREEN_ANIMATION_DURATION_IN = 300
private const val SCREEN_ANIMATION_DURATION_OUT = 100
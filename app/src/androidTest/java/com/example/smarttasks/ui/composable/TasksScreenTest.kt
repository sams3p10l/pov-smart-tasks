package com.example.smarttasks.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.theme.SmartTasksTheme
import org.junit.Rule
import org.junit.Test

class TasksScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tasksList_showsEmptyState() {
        composeTestRule.setContent {
            SmartTasksTheme {
                TasksList(
                    uiData = TaskScreenUiModel("Today", emptyList()),
                    onCardClick = {},
                    onLeftArrowClick = {},
                    onRightArrowClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("No tasks for today!").assertIsDisplayed()
    }

    @Test
    fun tasksScreen_showsTasks_whenListNotEmpty() {
        val sampleUi = TaskScreenUiModel(
            date = "Today",
            tasks = listOf(
                TaskScreenUiModel.TaskUi(
                    id = "1",
                    title = "Sample Task",
                    description = "Do something",
                    dueDate = "Sep 20 2025",
                    daysLeft = "3",
                    statusIconDrawableRes = null
                )
            )
        )

        composeTestRule.setContent {
            SmartTasksTheme {
                TasksList(
                    uiData = sampleUi,
                    onCardClick = {},
                    onLeftArrowClick = {},
                    onRightArrowClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Sample Task")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Sep 20 2025")
            .assertIsDisplayed()
    }
}

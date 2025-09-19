package com.example.smarttasks.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.theme.SmartTasksTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDetailsContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createTestUiModel(
        status: TaskStatus = TaskStatus.UNRESOLVED,
        comment: String = ""
    ): TaskDetailsUiModel {
        return TaskDetailsUiModel(
            title = "Test Title",
            dueDate = "Apr 23 2016",
            daysLeft = "3",
            description = "Some description",
            status = status,
            comment = comment
        )
    }

    @Test
    fun rendersTitleAndDescription() {
        composeTestRule.setContent {
            SmartTasksTheme {
                TaskDetailsContent(
                    uiData = createTestUiModel(),
                    updateTask = { _, _ -> },
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Some description").assertIsDisplayed()
    }

    @Test
    fun showsQuestionDialog_whenResolveClicked() {
        composeTestRule.setContent {
            SmartTasksTheme {
                TaskDetailsContent(
                    uiData = createTestUiModel(comment = ""),
                    updateTask = { _, _ -> },
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Resolve").performClick()
        composeTestRule.onNodeWithText("Do you want to leave a comment?").assertIsDisplayed()
    }

    @Test
    fun callsUpdateTask_whenDialogDismissed() {
        var calledStatus: TaskStatus? = null
        var calledComment: String? = null

        composeTestRule.setContent {
            SmartTasksTheme {
                TaskDetailsContent(
                    uiData = createTestUiModel(comment = ""),
                    updateTask = { status, comment ->
                        calledStatus = status
                        calledComment = comment
                    },
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Resolve").performClick()
        composeTestRule.onNodeWithText("No").performClick()

        assertEquals(TaskStatus.RESOLVED, calledStatus)
        assertNull(calledComment)
    }

    @Test
    fun callsUpdateTask_withComment_whenSaved() {
        var calledStatus: TaskStatus? = null
        var calledComment: String? = null

        composeTestRule.setContent {
            SmartTasksTheme {
                TaskDetailsContent(
                    uiData = createTestUiModel(comment = ""),
                    updateTask = { status, comment ->
                        calledStatus = status
                        calledComment = comment
                    },
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Resolve").performClick()
        composeTestRule.onNodeWithText("Yes").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("Test comment")
        composeTestRule.onNodeWithText("Save").performClick()

        assertEquals(TaskStatus.RESOLVED, calledStatus)
        assertEquals("Test comment", calledComment)
    }
}
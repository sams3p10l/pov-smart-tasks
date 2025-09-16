package com.example.smarttasks.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttasks.R
import com.example.smarttasks.ui.model.TaskUiModel
import com.example.smarttasks.ui.theme.PaddingExtraLarge
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.White
import com.example.smarttasks.ui.theme.Yellow

@Composable
fun TasksScreen(
    uiData: List<TaskUiModel>,
    modifier: Modifier = Modifier
) {
    var date by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow)
            .padding(start = PaddingMedium, end = PaddingMedium, top = PaddingLarge)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.arrow_left),
                contentDescription = "Arrow to the left"
            )
            Text(
                text = date,
                color = White
            )
            Image(
                painterResource(R.drawable.arrow_right),
                contentDescription = "Arrow to the right"
            )
        }
        Spacer(Modifier.height(PaddingExtraLarge))
        LazyColumn {
            items(uiData.size) {
                TaskCard(uiData[it])
                Spacer(Modifier.height(PaddingMedium))
            }
        }
    }
}

@Preview
@Composable
fun PreviewTasksScreen() {
    SmartTasksTheme {
        TasksScreen(
            listOf(
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                ),
                TaskUiModel(
                    "Task Title",
                    "",
                    dueDate = "Apr 23 2016",
                    daysLeft = "12"
                )
            )
        )
    }
}
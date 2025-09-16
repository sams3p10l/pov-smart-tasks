package com.example.smarttasks.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttasks.R
import com.example.smarttasks.ui.model.TaskUiModel
import com.example.smarttasks.ui.theme.CardHeightMin
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.PaddingExtraLarge
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.Red
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.White
import com.example.smarttasks.ui.theme.Yellow
import com.example.smarttasks.ui.viewmodel.TasksViewModel

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: TasksViewModel = hiltViewModel()
    val uiData: State<List<TaskUiModel>> = viewModel.tasks.collectAsState()
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
            items(uiData.value.size) {
                TaskCard(uiData.value[it])
                Spacer(Modifier.height(PaddingMedium))
            }
        }
    }
}

@Composable
fun TaskCard(
    uiData: TaskUiModel,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = CardHeightMin),
        shape = RoundedCornerShape(CornerRadius)
    ) {
        Column(
            modifier = Modifier.padding(PaddingMedium)
        ) {
            Text(
                text = uiData.title,
                style = MaterialTheme.typography.titleLarge,
                color = Red
            )
            Spacer(Modifier.height(PaddingSmall))
            Divider()
            Spacer(Modifier.height(PaddingMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Due date",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Days left",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiData.dueDate,
                    style = MaterialTheme.typography.titleLarge,
                    color = Red
                )
                Text(
                    text = uiData.daysLeft,
                    style = MaterialTheme.typography.titleLarge,
                    color = Red
                )
            }
        }
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    SmartTasksTheme {
        TaskCard(
            TaskUiModel(
                "Task Title",
                "",
                dueDate = "Apr 23 2016",
                daysLeft = "12"
            )
        )
    }
}
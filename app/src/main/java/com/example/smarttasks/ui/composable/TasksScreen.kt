package com.example.smarttasks.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttasks.R
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.model.TaskUiModel
import com.example.smarttasks.ui.theme.CardHeightMin
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.Headline
import com.example.smarttasks.ui.theme.PaddingExtraLarge
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.Red
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.SpacerExtraLarge
import com.example.smarttasks.ui.theme.SpacerLarge
import com.example.smarttasks.ui.theme.TitleExtraLarge
import com.example.smarttasks.ui.theme.White
import com.example.smarttasks.ui.theme.Yellow
import com.example.smarttasks.ui.viewmodel.TasksViewModel

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
) {
    val viewModel: TasksViewModel = hiltViewModel()
    val uiState: State<TaskScreenUiModel> = viewModel.uiState.collectAsState()
    val data: TaskScreenUiModel = uiState.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow) //todo replace with MaterialTheme
            .padding(start = PaddingMedium, end = PaddingMedium, top = PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.arrow_left),
                contentDescription = "Arrow to the left",
                modifier = Modifier.clickable { viewModel.decrementDate() }
            )
            Text(
                text = data.date,
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontSize = TitleExtraLarge
            )
            Image(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = "Arrow to the right",
                modifier = Modifier.clickable { viewModel.incrementDate() }
            )
        }
        Spacer(Modifier.height(PaddingExtraLarge))
        if (data.tasks.isNotEmpty()) {
            LazyColumn {
                items(data.tasks.size) {
                    TaskCard(
                        uiData = data.tasks[it],
                        onClick = onCardClick
                    )
                    Spacer(Modifier.height(PaddingMedium))
                }
            }
        } else {
            Spacer(Modifier.height(SpacerExtraLarge))
            Image(
                painter = painterResource(R.drawable.empty_screen),
                contentDescription = "Illustration",
            )
            Spacer(Modifier.height(SpacerLarge))
            Text(
                text = "No tasks for ${data.date.lowercaseIfNotDate()}!",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontSize = Headline
            )
        }
    }
}

@Composable
fun TaskCard(
    uiData: TaskUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = CardHeightMin)
            .clickable(onClick = onClick),
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
            HorizontalDivider() //todo set correct color
            Spacer(Modifier.height(PaddingMedium))
            RowDueDate(
                dueDate = uiData.dueDate,
                daysLeft = uiData.daysLeft,
                accentColor = Red
            )
        }
    }
}

@Composable
fun RowDueDate(
    dueDate: String,
    daysLeft: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Due date",
            style = MaterialTheme.typography.bodyMedium
        )
        if (daysLeft.isNotNegative()) {
            Text(
                text = "Days left",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Spacer(Modifier.height(PaddingSmall))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dueDate,
            style = MaterialTheme.typography.titleLarge,
            color = accentColor
        )
        if (daysLeft.isNotNegative()) {
            Text(
                text = daysLeft,
                style = MaterialTheme.typography.titleLarge,
                color = accentColor
            )
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
            ),
            onClick = {}
        )
    }
}

private fun String.isNotNegative() = !this.startsWith("-")
private fun String.lowercaseIfNotDate() = if (this.any { it.isDigit() }) this else this.lowercase()
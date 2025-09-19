package com.example.smarttasks.ui.composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttasks.R
import com.example.smarttasks.ui.model.TaskScreenUiModel
import com.example.smarttasks.ui.model.UiState
import com.example.smarttasks.ui.theme.CardHeightMin
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.Headline
import com.example.smarttasks.ui.theme.PaddingExtraLarge
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.SmartTasksTheme
import com.example.smarttasks.ui.theme.SpacerExtraLarge
import com.example.smarttasks.ui.theme.SpacerLarge
import com.example.smarttasks.ui.theme.TitleExtraLarge
import com.example.smarttasks.ui.viewmodel.TasksViewModel

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    onCardClick: (String) -> Unit,
) {
    val viewModel: TasksViewModel = hiltViewModel()
    val uiState: State<UiState<TaskScreenUiModel>> = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val uiData = uiState.value) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            is UiState.Success -> {
                TasksList(
                    uiData = uiData.data,
                    onLeftArrowClick = viewModel::decrementDate,
                    onRightArrowClick = viewModel::incrementDate,
                    onCardClick = onCardClick,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                LaunchedEffect(uiData.message) {
                    Toast.makeText(context, uiData.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun TasksList(
    uiData: TaskScreenUiModel,
    modifier: Modifier = Modifier,
    onLeftArrowClick: () -> Unit,
    onRightArrowClick: () -> Unit,
    onCardClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                contentDescription = stringResource(R.string.arrow_left_desc),
                modifier = Modifier.clickable { onLeftArrowClick() }
            )
            Text(
                text = uiData.date,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TitleExtraLarge
            )
            Image(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = stringResource(R.string.arrow_right_desc),
                modifier = Modifier.clickable { onRightArrowClick() }
            )
        }
        Spacer(Modifier.height(PaddingExtraLarge))
        if (uiData.tasks.isNotEmpty()) {
            LazyColumn {
                items(items = uiData.tasks, key = { it.id }) { task ->
                    TaskCard(
                        uiData = task,
                        onClick = { onCardClick(task.id) }
                    )
                    Spacer(Modifier.height(PaddingMedium))
                }
            }
        } else {
            Spacer(Modifier.height(SpacerExtraLarge))
            Image(
                painter = painterResource(R.drawable.empty_screen),
                contentDescription = stringResource(R.string.illustration_desc),
            )
            Spacer(Modifier.height(SpacerLarge))
            Text(
                text = stringResource(R.string.empty_tasks, uiData.date.lowercaseIfNotDate()),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = Headline
            )
        }
    }
}

@Composable
fun TaskCard(
    uiData: TaskScreenUiModel.TaskUi,
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
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = uiData.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                uiData.statusIconDrawableRes?.let {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(start = PaddingLarge),
                        painter = painterResource(it),
                        contentScale = FixedScale(0.25f),
                        contentDescription = stringResource(R.string.task_status_icon_desc)
                    )
                }
            }
            Spacer(Modifier.height(PaddingSmall))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(PaddingMedium))
            RowDueDate(
                dueDate = uiData.dueDate,
                daysLeft = uiData.daysLeft,
                accentColor = MaterialTheme.colorScheme.secondary
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
            text = stringResource(R.string.due_date_label),
            style = MaterialTheme.typography.bodyMedium
        )
        if (daysLeft.isNotNegative()) {
            Text(
                text = stringResource(R.string.days_left_label),
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
            TaskScreenUiModel.TaskUi(
                id = "id123",
                "Task Title",
                "",
                dueDate = "Apr 23 2016",
                daysLeft = "12",
                statusIconDrawableRes = R.drawable.btn_resolved
            ),
            onClick = {}
        )
    }
}

private fun String.isNotNegative() = !this.startsWith("-")
private fun String.lowercaseIfNotDate() = if (this.any { it.isDigit() }) this else this.lowercase()
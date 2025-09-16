package com.example.smarttasks.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttasks.ui.model.TaskUiModel
import com.example.smarttasks.ui.theme.CardHeightMin
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.Red
import com.example.smarttasks.ui.theme.SmartTasksTheme

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
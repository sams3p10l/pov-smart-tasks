package com.example.smarttasks.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.smarttasks.R
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.Green
import com.example.smarttasks.ui.theme.Headline
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

@Composable
fun TaskDetailsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow) //todo replace with MaterialTheme
            .padding(horizontal = PaddingMedium, vertical = PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painterResource(R.drawable.arrow_left),
                contentDescription = "Arrow to the left",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onBack)
            )
            Text(
                text = "Task Detail",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontSize = TitleExtraLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(SpacerLarge))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.task_details),
                contentDescription = "Background illustration",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(
                        top = SpacerExtraLarge,
                        start = PaddingMedium,
                        end = PaddingMedium,
                        bottom = PaddingMedium
                    )
            ) {
                Text(
                    text = "Task Title",
                    color = Green,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = Headline
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider() //todo set correct color
                Spacer(Modifier.height(PaddingMedium))
                RowDueDate(
                    dueDate = "Apr 23 2016",
                    daysLeft = "0",
                    accentColor = Green
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider() //todo set correct color
                Spacer(Modifier.height(PaddingMedium))
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec laoreet eget elit eget tempus. Fusce luctus dignissim mi, id mattis lectus sollicitudin ut. Suspendisse vel quam vel turpis mollis malesuada. Ut elementum, turpis nec tincidunt feugiat, quam quam egestas diam",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 14.sp,
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider() //todo set correct color
                Spacer(Modifier.weight(0.5f))
//                Spacer(Modifier.height(PaddingMedium))
                Text(
                    text = "Resolved",
                    style = MaterialTheme.typography.titleLarge,
                    color = Green
                )
            }
        }
        Spacer(Modifier.height(PaddingMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
        ) {
           ResolveButton(
               text = "Resolve",
               color = Green
           ) { }
           ResolveButton(
               text = "Can't resolve",
               color = Red
           ) { }
        }
        Image(
            painter = painterResource(R.drawable.sign_resolved),
            contentDescription = "Task status symbol"
        )
    }
}

@Composable
private fun RowScope.ResolveButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(CornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = White
        ),
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun PreviewTaskDetailsScreen() {
    SmartTasksTheme {
        TaskDetailsScreen { }
    }
}
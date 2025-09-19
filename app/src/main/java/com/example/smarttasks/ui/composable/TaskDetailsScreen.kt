package com.example.smarttasks.ui.composable

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.smarttasks.R
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.Green
import com.example.smarttasks.ui.theme.Headline
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.Red
import com.example.smarttasks.ui.theme.SpacerExtraLarge
import com.example.smarttasks.ui.theme.SpacerLarge
import com.example.smarttasks.ui.theme.TitleExtraLarge
import com.example.smarttasks.ui.theme.White
import com.example.smarttasks.ui.theme.Yellow
import com.example.smarttasks.ui.viewmodel.TaskDetailsViewModel

@Composable
fun TaskDetailsScreen(
    taskId: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val viewModel: TaskDetailsViewModel = hiltViewModel()
    val uiState: State<TaskDetailsUiModel?> = viewModel.uiState.collectAsState()
    val data = uiState.value

    BackHandler {
        onBack()
    }

    LaunchedEffect(taskId) {
        viewModel.setTaskId(taskId)
    }

    var showQuestionDialog by remember { mutableStateOf<TaskStatus?>(null) }
    var showCommentDialog by remember { mutableStateOf<TaskStatus?>(null) }
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow) //todo replace with MaterialTheme
            .padding(horizontal = PaddingMedium, vertical = PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showQuestionDialog != null) {
            QuestionDialog(
                onConfirm = {
                    showCommentDialog = showQuestionDialog
                    showQuestionDialog = null
                },
                onDismiss = {
                    viewModel.updateTask(taskId, showQuestionDialog!!)
                    showQuestionDialog = null
                }
            )
        }
        if (showCommentDialog != null) {
            CommentDialog(
                value = commentText,
                onValueChange = { commentText = it },
                onConfirm = {
                    viewModel.updateTask(taskId, showCommentDialog!!, commentText)
                    showCommentDialog = null
                    commentText = ""
                },
                onDismiss = {
                    showCommentDialog = null
                    commentText = ""
                }
            )
        }

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
        if (data != null) {
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
                    AutoResizeTitle(
                        text = data.title,
                        color = data.accentColor,
                        style = MaterialTheme.typography.titleLarge,
                        initialFontSize = Headline,
                        minFontSize = TitleExtraLarge
                    )
                    Spacer(Modifier.height(PaddingMedium))
                    HorizontalDivider() //todo set correct color
                    Spacer(Modifier.height(PaddingMedium))
                    RowDueDate(
                        dueDate = data.dueDate,
                        daysLeft = data.daysLeft,
                        accentColor = data.accentColor
                    )
                    Spacer(Modifier.height(PaddingMedium))
                    HorizontalDivider() //todo set correct color
                    Spacer(Modifier.height(PaddingMedium))
                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 14.sp,
                    )
                    Spacer(Modifier.height(PaddingMedium))
                    HorizontalDivider() //todo set correct color
                    if (data.comment.isNotEmpty()) {
                        Spacer(Modifier.height(PaddingMedium))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                    append("Comment: ")
                                }
                                append(data.comment)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Spacer(Modifier.weight(0.5f))
                    Text(
                        text = data.statusText,
                        style = MaterialTheme.typography.titleLarge,
                        color = data.statusTextColor
                    )
                }
            }
            Spacer(Modifier.height(PaddingMedium))
            when (data.status) {
                TaskStatus.UNRESOLVED -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
                    ) {
                        ResolveButton(
                            text = "Resolve",
                            color = Green
                        ) {
                            if (data.comment.isEmpty()) {
                                showQuestionDialog = TaskStatus.RESOLVED
                            } else {
                                viewModel.updateTask(taskId, TaskStatus.RESOLVED)
                            }
                        }
                        ResolveButton(
                            text = "Can't resolve",
                            color = Red
                        ) {
                            if (data.comment.isEmpty()) {
                                showQuestionDialog = TaskStatus.CANT_RESOLVE
                            } else {
                                viewModel.updateTask(taskId, TaskStatus.CANT_RESOLVE)
                            }
                        }
                    }
                }

                TaskStatus.RESOLVED -> {
                    TaskStatusImage(R.drawable.sign_resolved)
                }

                TaskStatus.CANT_RESOLVE -> {
                    TaskStatusImage(R.drawable.unresolved_sign)
                }
            }
        } else {
            CircularProgressIndicator(
                color = White
            )
        }
    }
}

@Composable
private fun AutoResizeTitle(
    text: String,
    color: Color,
    style: TextStyle,
    initialFontSize: TextUnit,
    minFontSize: TextUnit,
    modifier: Modifier = Modifier,
    maxLines: Int = 3,
) {
    var fontSize by remember { mutableStateOf(initialFontSize) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = color,
        style = style.copy(fontSize = fontSize),
        onTextLayout = { result ->
            if (result.lineCount > maxLines && fontSize > minFontSize) {
                fontSize *= 0.9f
            } else {
                readyToDraw = true
            }
        },
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        }
    )
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

@Composable
private fun TaskStatusImage(
    @DrawableRes drawable: Int
) {
    Spacer(Modifier.height(SpacerLarge))
    Image(
        painter = painterResource(drawable),
        contentDescription = "Task status symbol"
    )
}

@Composable
private fun QuestionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Text(
                text = "Do you want to leave a comment?",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Red
                ),
                onClick = { onConfirm() }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Red
                ),
                onClick = { onDismiss() }
            ) {
                Text("No")
            }
        }
    )
}

@Composable
private fun CommentDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Column {
                Text(
                    text = "Leave a comment here:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(PaddingSmall))
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = false,
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Red
                ),
                onClick = onConfirm
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Red
                ),
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}
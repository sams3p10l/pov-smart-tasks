package com.example.smarttasks.ui.composable

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.smarttasks.R
import com.example.smarttasks.domain.model.TaskStatus
import com.example.smarttasks.ui.model.TaskDetailsUiModel
import com.example.smarttasks.ui.model.UiState
import com.example.smarttasks.ui.theme.CornerRadius
import com.example.smarttasks.ui.theme.DescriptionLineHeight
import com.example.smarttasks.ui.theme.Headline
import com.example.smarttasks.ui.theme.PaddingLarge
import com.example.smarttasks.ui.theme.PaddingMedium
import com.example.smarttasks.ui.theme.PaddingSmall
import com.example.smarttasks.ui.theme.SpacerExtraLarge
import com.example.smarttasks.ui.theme.SpacerLarge
import com.example.smarttasks.ui.theme.TitleExtraLarge
import com.example.smarttasks.ui.viewmodel.TaskDetailsViewModel

@Composable
fun TaskDetailsScreen(
    taskId: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val viewModel: TaskDetailsViewModel = hiltViewModel()
    val uiState: State<UiState<TaskDetailsUiModel>> = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        onBack()
    }

    LaunchedEffect(taskId) {
        viewModel.setTaskId(taskId)
    }

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
                TaskDetailsContent(
                    modifier = modifier,
                    uiData = uiData.data,
                    onBack = onBack,
                    updateTask = { status, comment ->
                        viewModel.updateTask(taskId, status, comment)
                    }
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
fun TaskDetailsContent(
    uiData: TaskDetailsUiModel,
    modifier: Modifier = Modifier,
    updateTask: (TaskStatus, String?) -> Unit,
    onBack: () -> Unit
) {
    var showQuestionDialog by remember { mutableStateOf<TaskStatus?>(null) }
    var showCommentDialog by remember { mutableStateOf<TaskStatus?>(null) }
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                    updateTask(showQuestionDialog!!, null)
                    showQuestionDialog = null
                }
            )
        }
        if (showCommentDialog != null) {
            CommentDialog(
                value = commentText,
                onValueChange = { commentText = it },
                onConfirm = {
                    updateTask(showCommentDialog!!, commentText)
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
                contentDescription = stringResource(R.string.arrow_left_desc),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onBack)
            )
            Text(
                text = stringResource(R.string.task_detail_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TitleExtraLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(SpacerLarge))

        val statusUi = resolveStatusUi(uiData.status)
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.task_details),
                contentDescription = stringResource(R.string.task_detail_illustration_desc),
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
                    text = uiData.title,
                    color = statusUi.accentColor,
                    style = MaterialTheme.typography.titleLarge,
                    initialFontSize = Headline,
                    minFontSize = TitleExtraLarge
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Spacer(Modifier.height(PaddingMedium))
                RowDueDate(
                    dueDate = uiData.dueDate,
                    daysLeft = uiData.daysLeft,
                    accentColor = statusUi.accentColor
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Spacer(Modifier.height(PaddingMedium))
                Text(
                    text = uiData.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = DescriptionLineHeight,
                )
                Spacer(Modifier.height(PaddingMedium))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                if (uiData.comment.isNotEmpty()) {
                    Spacer(Modifier.height(PaddingMedium))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append(stringResource(R.string.comment_prefix))
                            }
                            append(" ${uiData.comment}")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(Modifier.weight(0.5f))
                Text(
                    text = stringResource(statusUi.textRes),
                    style = MaterialTheme.typography.titleLarge,
                    color = statusUi.textColor
                )
            }
        }
        Spacer(Modifier.height(PaddingMedium))
        when (uiData.status) {
            TaskStatus.UNRESOLVED -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
                ) {
                    ResolveButton(
                        text = stringResource(R.string.resolve_button),
                        color = MaterialTheme.colorScheme.tertiary
                    ) {
                        if (uiData.comment.isEmpty()) {
                            showQuestionDialog = TaskStatus.RESOLVED
                        } else {
                            updateTask(TaskStatus.RESOLVED, null)
                        }
                    }
                    ResolveButton(
                        text = stringResource(R.string.cant_resolve_button),
                        color = MaterialTheme.colorScheme.secondary
                    ) {
                        if (uiData.comment.isEmpty()) {
                            showQuestionDialog = TaskStatus.CANT_RESOLVE
                        } else {
                            updateTask(TaskStatus.CANT_RESOLVE, null)
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
    val contentColor = when (color) {
        MaterialTheme.colorScheme.secondary -> MaterialTheme.colorScheme.onSecondary
        MaterialTheme.colorScheme.tertiary -> MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.onPrimary
    }

    Button(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(CornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = contentColor
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
        contentDescription = stringResource(R.string.task_status_symbol_desc)
    )
}

private data class StatusUi(
    val textRes: Int,
    val textColor: Color,
    val accentColor: Color
)

@Composable
private fun resolveStatusUi(status: TaskStatus): StatusUi {
    return when (status) {
        TaskStatus.RESOLVED -> StatusUi(
            textRes = R.string.status_resolved,
            textColor = MaterialTheme.colorScheme.tertiary,
            accentColor = MaterialTheme.colorScheme.tertiary
        )

        TaskStatus.UNRESOLVED -> StatusUi(
            textRes = R.string.status_unresolved,
            textColor = MaterialTheme.colorScheme.primary,
            accentColor = MaterialTheme.colorScheme.secondary
        )

        TaskStatus.CANT_RESOLVE -> StatusUi(
            textRes = R.string.status_unresolved,
            textColor = MaterialTheme.colorScheme.secondary,
            accentColor = MaterialTheme.colorScheme.secondary
        )
    }
}
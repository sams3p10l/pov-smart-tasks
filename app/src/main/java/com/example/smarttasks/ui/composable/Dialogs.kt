package com.example.smarttasks.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.smarttasks.R
import com.example.smarttasks.ui.theme.PaddingSmall

@Composable
fun QuestionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Text(
                text = stringResource(R.string.leave_comment_prompt),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = { onConfirm() }
            ) {
                Text(stringResource(R.string.dialog_yes))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = { onDismiss() }
            ) {
                Text(stringResource(R.string.dialog_no))
            }
        }
    )
}

@Composable
fun CommentDialog(
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
                    text = stringResource(R.string.leave_comment_label),
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
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = onConfirm
            ) {
                Text(stringResource(R.string.dialog_save))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }
    )
}
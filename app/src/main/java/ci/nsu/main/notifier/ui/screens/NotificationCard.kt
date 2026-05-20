package ci.nsu.main.notifier.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.main.notifier.data.model.NotificationItem
import ci.nsu.main.notifier.utils.DateUtils

@Composable
fun NotificationCard(
    notification: NotificationItem,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = DateUtils.formatDateTime(notification.timestamp),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = notification.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }

            Checkbox(
                checked = notification.isEnabled,
                onCheckedChange = { onToggle() }
            )

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}
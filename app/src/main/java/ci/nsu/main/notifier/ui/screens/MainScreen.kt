package ci.nsu.main.notifier.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.main.notifier.data.model.NotificationItem
import ci.nsu.main.notifier.utils.DateUtils
import ci.nsu.main.notifier.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: NotificationViewModel,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val notifications by viewModel.notifications.collectAsState()

    // Простой вывод для отладки
    LaunchedEffect(notifications) {
        println("🔔 MainScreen: notifications size = ${notifications.size}")
        notifications.forEach {
            println("🔔 Notification: ${it.title} at ${it.timestamp}")
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Планировщик уведомлений") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (notifications.isEmpty()) {
                Text(
                    text = "Нет уведомлений.\nНажмите + чтобы добавить",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notifications) { notification ->
                        // Простая карточка для теста
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = DateUtils.formatDateTime(notification.timestamp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = notification.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = notification.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Row {
                                    Checkbox(
                                        checked = notification.isEnabled,
                                        onCheckedChange = { viewModel.toggleEnabled(notification) }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(onClick = { onEditClick(notification.id) }) {
                                        Text("Редактировать")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(onClick = { viewModel.deleteNotification(notification) }) {
                                        Text("Удалить")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
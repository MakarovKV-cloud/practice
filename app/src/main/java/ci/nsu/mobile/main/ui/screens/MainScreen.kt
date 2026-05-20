package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.mobile.main.data.model.NotificationItem
import ci.nsu.mobile.main.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: NotificationViewModel,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val notifications by viewModel.notifications.collectAsState()

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
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Количество уведомлений: ${notifications.size}",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (notifications.isEmpty()) {
                    Text(
                        text = "Нет уведомлений",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Нажмите + чтобы добавить",
                        fontSize = 14.sp,
                        color = Color(0xFF6200EE)
                    )
                } else {
                    LazyColumn {
                        items(notifications) { notification ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(notification.title, fontSize = 16.sp, color = Color.Black)
                                        Text(notification.description, fontSize = 12.sp, color = Color.Gray)
                                    }
                                    Button(onClick = { onEditClick(notification.id) }) {
                                        Text("Редактировать")
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
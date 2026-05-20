package ci.nsu.main.notifier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.main.notifier.data.model.NotificationItem
import ci.nsu.main.notifier.data.repository.NotificationRepository
import ci.nsu.main.notifier.notification.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
    private val scheduler: NotificationScheduler
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAllNotifications().collect { list ->
                    _notifications.value = list
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNotification(notification: NotificationItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val id = repository.insert(notification)
                val savedNotification = notification.copy(id = id)
                if (savedNotification.isEnabled) {
                    scheduler.scheduleNotification(savedNotification)
                }
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка добавления: ${e.message}"
            }
        }
    }

    fun updateNotification(notification: NotificationItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val oldNotification = repository.getNotificationById(notification.id)
                repository.update(notification)

                scheduler.cancelNotification(notification.id)
                if (notification.isEnabled) {
                    scheduler.scheduleNotification(notification)
                }
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка обновления: ${e.message}"
            }
        }
    }

    fun deleteNotification(notification: NotificationItem) {
        viewModelScope.launch {
            try {
                scheduler.cancelNotification(notification.id)
                repository.delete(notification)
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка удаления: ${e.message}"
            }
        }
    }

    fun toggleEnabled(notification: NotificationItem) {
        viewModelScope.launch {
            try {
                val updated = notification.copy(isEnabled = !notification.isEnabled)
                repository.update(updated)

                if (updated.isEnabled) {
                    if (updated.timestamp > System.currentTimeMillis()) {
                        scheduler.scheduleNotification(updated)
                    }
                } else {
                    scheduler.cancelNotification(updated.id)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка изменения статуса: ${e.message}"
            }
        }
    }

    suspend fun checkExistsAtTimestamp(timestamp: Long): Boolean {
        return repository.existsAtTimestamp(timestamp)
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
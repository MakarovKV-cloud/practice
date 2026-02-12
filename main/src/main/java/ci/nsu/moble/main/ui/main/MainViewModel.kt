package ci.nsu.moble.main.ui.main

import android.graphics.Color.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class MainViewModel : ViewModel() {
    val colors = listOf<Int>(RED, YELLOW, BLUE, GREEN, CYAN, MAGENTA)
    val colorNames = listOf("RED", "YELLOW", "BLUE", "GREEN", "CYAN", "MAGENTA")

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun changeColor(inputText: String) {
        val upperInput = inputText.uppercase()

        val index = colorNames.indexOf(upperInput)
        if (index != -1) {
            _backgroundColor.value = colors[index]
            _message.value = "Цвет изменен на $upperInput"
        } else {
            _message.value = "Цвет '$inputText' не найден. Доступные цвета: ${colorNames.joinToString()}"
            _backgroundColor.value = WHITE
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}
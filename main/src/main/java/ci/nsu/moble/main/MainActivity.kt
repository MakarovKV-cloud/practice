package ci.nsu.moble.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView


class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        val colorMap = mapOf(
            "RED" to 0xFFFF0000,
            "YELLOW" to 0xFFFFFF00,
            "GREEN" to 0xFF00FF00,
            "BLUE" to 0xFF0000FF
        )

        val TextColor = findViewById<EditText>(R.id.textcolor)
        val ColorBut = findViewById<Button>(R.id.button)
        val ColorsList = findViewById<ListView>(R.id.ListColors)

        val colorNames = colorMap.keys.toList()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            colorNames
        )
        ColorsList.adapter = adapter

        ColorsList.setOnItemClickListener { parent, view, position, id ->
            val selectedColorName = colorNames[position]
            val colorValue = colorMap[selectedColorName]

            if (colorValue != null) {
                ColorBut.setBackgroundColor(colorValue.toInt())

                TextColor.setText(selectedColorName)
            }
        }

        ColorBut.setOnClickListener {
            val inputText = TextColor.text.toString().uppercase()
            val colorInt = colorMap[inputText]

            if (colorInt != null) {
                ColorBut.setBackgroundColor(colorInt.toInt())
            } else {
                TextColor.error = "Цвет не найден"
            }
        }
    }
}
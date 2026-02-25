package ci.nsu.moble.main

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        val colorMap = mapOf(
            "RED" to 0xFFFF0000,
            "ORANGE" to rgb(255, 140, 0),
            "YELLOW" to 0xFFFFFF00,
            "GREEN" to 0xFF00FF00,
            "LIGHT BLUE" to rgb(0, 183, 255),
            "BLUE" to 0xFF0000FF,
            "PURPLE" to rgb(140,9,217)
        )

        val TextColor = findViewById<EditText>(R.id.textcolor)
        val ColorBut = findViewById<Button>(R.id.button)
        val ColorsList = findViewById<ListView>(R.id.ListColors)

        val colorNames = colorMap.keys.toList()

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            colorNames
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view as TextView
                val colorName = colorNames[position]
                val colorValue = colorMap[colorName]


                if (colorValue != null) {
                    textView.setBackgroundColor(colorValue.toInt())

                }

                return view
            }
        }

        ColorsList.adapter = adapter

        ColorsList.setOnItemClickListener { parent, view, position, id ->
            val selectedColorName = colorNames[position]
            val colorValue = colorMap[selectedColorName]

            if (colorValue != null) {
                ColorBut.setBackgroundColor(colorValue.toInt())
                TextColor.setText(selectedColorName)
                Log.d(TAG, "Цвет найден. Всё хорошо")
            }
        }

        ColorBut.setOnClickListener {
            val inputText = TextColor.text.toString().uppercase()
            val colorInt = colorMap[inputText]

            if (colorInt != null) {
                ColorBut.setBackgroundColor(colorInt.toInt())
                Log.d(TAG, "Цвет найден. Всё хорошо")
            } else {
                TextColor.error = "Цвет не найден"
                Log.e(TAG, "Цвет не найден")
            }
        }
    }
}
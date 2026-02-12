package ci.nsu.moble.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ci.nsu.moble.main.ui.main.RainbowFragment

class Rainbow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rainbow)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RainbowFragment.newInstance())
                .commitNow()
        }
    }
}
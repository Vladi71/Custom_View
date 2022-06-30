package ru.netology.custom_view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.custom_view.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StatsView>(R.id.stats).let {
            it.data = listOf(
                20F,
                20F,
                20F,
                15F
            )
        }
    }
}
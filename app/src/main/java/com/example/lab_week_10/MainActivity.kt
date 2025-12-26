package com.example.lab_week_10

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lab_week_10.data.TotalDatabase
import com.example.lab_week_10.data.TotalEntity
import com.example.lab_week_10.data.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var totalViewModel: TotalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        totalViewModel = ViewModelProvider(this)[TotalViewModel::class.java]

        val database = TotalDatabase.getDatabase(this)
        totalViewModel.setDao(database.totalDao())

        val textTop = findViewById<TextView>(R.id.textTotalTop)
        val textBottom = findViewById<TextView>(R.id.textTotalBottom)
        val btnPress = findViewById<Button>(R.id.btnPress)

        totalViewModel.total.observe(this) { value ->
            textTop.text = "Total: $value"
            textBottom.text = "Total: $value"
        }

        btnPress.setOnClickListener {
            totalViewModel.addNumber(1)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textTimestamp = findViewById<TextView>(R.id.textTimestamp)

    }


    override fun onPause() {
        super.onPause()

        val currentTotal = totalViewModel.total.value ?: 0
        val dateNow = Date().toString()

        lifecycleScope.launch {
            totalViewModel.totalDao.insertTotal(
                TotalEntity(
                    total = TotalObject(
                        value = currentTotal,
                        date = dateNow
                    )
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            val lastTotal = totalViewModel.totalDao.getLastTotal()
            lastTotal?.let {
                val dateText = it.total.date

                // tampil di layar
                findViewById<TextView>(R.id.textTimestamp).text =
                    "Last updated:\n$dateText"

                // (opsional) Toast tetap ada
                Toast.makeText(
                    this@MainActivity,
                    dateText,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}

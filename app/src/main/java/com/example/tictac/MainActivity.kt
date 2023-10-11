package com.example.tictac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var someActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val resultData = data.getIntExtra("my_result_key", -1)
                println("WIN $resultData")
                val textWin = findViewById<TextView>(R.id.textWin)
                val win = when (resultData) {
                    1 -> "WIN YOU"
                    2 -> "Opponent WIN"
                    else -> "Мир, дружба, жвачка!"
                }
                textWin.text = win
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, GameActivity::class.java)
        val button: Button = findViewById<View>(R.id.start) as Button
        button.setOnClickListener {
            intent.putExtra("my_result_key", "0")
            someActivityResultLauncher.launch(intent)
        }
        val buttonAI: Button = findViewById<View>(R.id.startAI) as Button
        buttonAI.setOnClickListener {
            intent.putExtra("my_result_key", "1")
            someActivityResultLauncher.launch(intent)
        }
    }
}
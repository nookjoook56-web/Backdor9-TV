package com.example.backdor9.util

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.backdor9.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👇 BURAYA
        setContentView(R.layout.activity_login)

        // 👇 VE BURAYA
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            // login işlemi burada yapılır
        }
    }
}
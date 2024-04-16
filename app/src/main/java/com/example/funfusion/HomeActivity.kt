package com.example.funfusion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val accountInfosBtn = findViewById<Button>(R.id.accountInfosBtn)

        accountInfosBtn.setOnClickListener {
            startActivity(Intent(this,TabbarActivity::class.java))
        }
    }
}
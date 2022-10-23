package com.example.apptransporte.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.apptransporte.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnIngresar: Button = findViewById(R.id.btnLogin)

        btnIngresar.setOnClickListener{
            this.goOptions()
        }


    }

    private fun goOptions(){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

}
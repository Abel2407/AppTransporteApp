package com.example.apptransporte.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.apptransporte.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail: EditText = findViewById(R.id.etUser)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val db = FirebaseAuth.getInstance()
        val dbStore = FirebaseFirestore.getInstance()

        val btnIngresar: Button = findViewById(R.id.btnLogin)

        val linkRegistrar: TextView = findViewById(R.id.tvcrearcuenta)

        btnIngresar.setOnClickListener{
            val correo = etEmail.text.toString()
            val clave = etPassword.text.toString()

            db.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){

                        dbStore.collection("usuarios").document( correo)
                            .get()
                            .addOnSuccessListener { document ->

                                if (document.exists())
                                {
                                    val toast = Toast.makeText(this, "Acceso Permitido como usuario", Toast.LENGTH_LONG)
                                    toast.show()

                                    val intent : Intent = Intent(this,UserActivity::class.java)
                                    intent.putExtra("action", "login");
                                    intent.putExtra("user", correo);
                                    startActivity(intent)

                                }else{
                                    val toast = Toast.makeText(this, "Acceso Permitido como admin", Toast.LENGTH_LONG)
                                    toast.show()
                                    startActivity( Intent(this,MainActivity::class.java))
                                }

                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(this,"Error . Intente nuevamente", Toast.LENGTH_LONG).show()
                            }


                    }else{
                        Toast.makeText(this,"Correo y/o clave incorrecta", Toast.LENGTH_LONG).show()
                    }
                }
        }

        linkRegistrar.setOnClickListener {
            val intent : Intent = Intent(this,UserActivity::class.java)
            intent.putExtra("action", "newuser");
            startActivity( intent)
        }


    }

}
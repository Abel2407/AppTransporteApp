package com.example.apptransporte.ui.fragments.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.apptransporte.R
import com.example.apptransporte.ui.Login
import com.example.apptransporte.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.example.apptransporte.ui.fragments.models.UserModel
import java.util.*


class RegistroClienteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_registro_cliente, container, false)

        val etNombres: EditText = view.findViewById(R.id.etNombres)
        val etDni: EditText = view.findViewById(R.id.etDni)
        val etTelefono: EditText =  view.findViewById(R.id.etTelefono)
        val etEmail: EditText =  view.findViewById(R.id.etEmail)
        val etDireccion: EditText =  view.findViewById(R.id.etDireccion)

        var tvUserError: TextView = view.findViewById(R.id.tvUserError)
        val dbStore = FirebaseFirestore.getInstance()
        val dbUser = FirebaseAuth.getInstance()

        val btnRegistrarUser: Button =  view.findViewById(R.id.btnRegistrarUser)

        btnRegistrarUser.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etDni.text.toString()

            var newuser= UserModel(
                etNombres.text.toString(),
                etDni.text.toString(),
                etTelefono.text.toString(),
                etEmail.text.toString(),
                etDireccion.text.toString()
            )

            dbUser.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((context as Activity?)!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = dbUser.currentUser
                        dbStore.collection("usuarios")
                            .document(email)
                            .set(newuser)
                            .addOnSuccessListener {
                                Toast.makeText((context as Activity?)!!,"Usuario creado correctamente.", Toast.LENGTH_LONG).show()
                                startActivity( Intent((context as Activity?)!!, Login::class.java))
                            }.addOnFailureListener {
                                Toast.makeText((context as Activity?)!!,"Ocurrió un error al registra el usuario...", Toast.LENGTH_LONG).show()
                            }

                    } else {
                        // If sign in fails, display a message to the user.
                        val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                        if(errorCode == "ERROR_EMAIL_ALREADY_IN_USE")
                        {
                            tvUserError.text = "Email ya se encuentra registrado."
                        }else{
                            tvUserError.text = errorCode
                        }


                    }
                }
        }

        return view
    }

}
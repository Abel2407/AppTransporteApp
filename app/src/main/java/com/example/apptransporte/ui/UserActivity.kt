package com.example.apptransporte.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apptransporte.R
import androidx.fragment.app.commit
import androidx.fragment.app.add
import com.example.apptransporte.ui.fragments.user.RegistroClienteFragment
import com.example.apptransporte.ui.fragments.user.ViajesFragment

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val action=intent.getStringExtra("action");
        if (savedInstanceState == null) {

            if(action == "newuser")
            {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<RegistroClienteFragment>(R.id.userFragmentContainerView)
                }
            }else if(action == "login"){
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<ViajesFragment>(R.id.userFragmentContainerView)
                }
            }

        }
    }
}
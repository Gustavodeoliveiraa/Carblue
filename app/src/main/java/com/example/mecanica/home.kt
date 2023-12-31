package com.example.mecanica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class home : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addclientebutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val home = Intent(this, home::class.java)
                    startActivity(home)
                    true
                }

                R.id.exit -> {
                    val exit = Intent(this, MainActivity::class.java)
                    startActivity(exit)
                    true

                }

                R.id.settings -> {
                    println("settings")
                    true
                }


                else -> false
            }
        }

//        addcliente
        addclientebutton = findViewById(R.id.addclienteclick)

        addclientebutton.setOnClickListener {
            val addcliente = Intent(this, addclientetela::class.java)
            startActivity(addcliente)
        }
    }
}


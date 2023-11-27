package com.example.mecanica

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class addclientetela : AppCompatActivity() {
    private lateinit var vertudo: Button
    private lateinit var add: Button
    private lateinit var datanasc: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addclientetela)

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
        vertudo = findViewById(R.id.vertodos)

        vertudo.setOnClickListener {
            println("motrandotudo")
            openDatePicker()

        }

        add = findViewById(R.id.add)

        add.setOnClickListener {
            var name: EditText
            var nomestring: String

            var rg: EditText
            var rgstring: String



            name = findViewById(R.id.nome)
            nomestring = name.text.toString()

            rg = findViewById(R.id.rg)
            rgstring = name.text.toString()



            println(nomestring)
            println(rgstring)
            println(datanasc.text.toString())
        }

        datanasc = findViewById(R.id.datanascimento)
        datanasc.setOnClickListener {
            openDatePicker()
        }

    }

    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.Base_Theme_Mecanica,
            { datePicker, year, month, day ->
                // Mostra o valor selecionado no EditText
                datanasc.setText("$day/$month/$year")
            },
            2023, 1, 20
        )
        datePickerDialog.show()
        println("Fechou o DatePicker") // Adicione este log
    }

}


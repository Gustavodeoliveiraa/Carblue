package com.example.mecanica

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class addclientetela : AppCompatActivity() {
    private lateinit var vertudo: Button
    private lateinit var add: Button
    private lateinit var datanasc: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addclientetela)
        val database: SQLiteDatabase = openOrCreateDatabase("DB_USUARIOS", MODE_PRIVATE, null)

        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS usuarios(nome VARCHAR, rg INT(10), nascimento VARCHAR)")

        }catch(e: Exception){
            e.printStackTrace()
        }

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
            val verclientes = Intent(this, seeAllClients::class.java)
            startActivity(verclientes)

        }

        add = findViewById(R.id.add)

        add.setOnClickListener {
            val name: EditText = findViewById(R.id.nome)
            val rg: EditText = findViewById(R.id.rg)

            val nomeUsuario = name.text.toString()
            val rgUsuario = rg.text.toString()
            val dataNascimentoUsuario = datanasc.text.toString()

            if (name.text.isNotEmpty() && rg.text.isNotEmpty() && datanasc.text.isNotEmpty()) {
                try {
                    // Use ? como marcadores de posição e passe os valores como argumentos para evitar SQL injection
                    database.execSQL("INSERT INTO usuarios (nome, rg, nascimento) VALUES (?, ?, ?)",
                        arrayOf(nomeUsuario, rgUsuario, dataNascimentoUsuario))

                    // Consulta para verificar os dados inseridos
                    val cursor = database.rawQuery("SELECT * FROM usuarios", null)

                    // Índices das colunas
                    val nameIndex = cursor.getColumnIndex("nome")
                    val rgIndex = cursor.getColumnIndex("rg")
                    val nascIndex = cursor.getColumnIndex("nascimento")

                    cursor.moveToFirst()

                    while (!cursor.isAfterLast) {
                        val nome = cursor.getString(nameIndex)
                        val rguser = cursor.getString(rgIndex)
                        val idadeuser = cursor.getString(nascIndex)

                        Log.i("resultado", "/nome: $nome/ $rguser/ $idadeuser")
                        cursor.moveToNext()
                    }
                    name.text.clear()
                    rg.text.clear()
                    datanasc.text = ""

                    cursor.close()
                    Toast.makeText(this, "Cliente adicionado com sucesso", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }


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


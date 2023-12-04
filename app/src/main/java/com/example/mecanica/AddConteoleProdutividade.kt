package com.example.mecanica

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddConteoleProdutividade : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var concluir: Button
    private lateinit var data: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_conteole_produtividade)

        try {
            val database: SQLiteDatabase = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS produtividade02(cod VARCHAR, funcionario VARCHAR, data VARCHAR, servico VARCHAR)")

        }catch(e: Exception){
            e.printStackTrace()
        }

        data = findViewById(R.id.data01)
        data.setOnClickListener {
            openDatePicker()
        }

        val codEditText = findViewById<EditText>(R.id.Cod)
        val funcionarioEditText = findViewById<EditText>(R.id.funcionario)
        val servicoEditText = findViewById<EditText>(R.id.servico)


        concluir = findViewById(R.id.concluir)
        concluir.setOnClickListener {

            val codValue = codEditText.text.toString()
            val funcionarioVale = funcionarioEditText.text.toString()
            val dataValue = data.text.toString()
            val servicoValue = servicoEditText.text.toString()

            println("$codValue $funcionarioVale  $dataValue $servicoValue")

            try {
                val database: SQLiteDatabase = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
                // Ajuste nos nomes das colunas
                val query = "INSERT INTO produtividade02 VALUES ('$codValue', '$funcionarioVale', '$dataValue', '$servicoValue')"
                database.execSQL(query)

                Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show()

                // Lembre-se de fechar o banco de dados quando terminar
                database.close()

                codEditText.text.clear()
                funcionarioEditText.text.clear()
                data.text = ""
                servicoEditText.text.clear()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
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


    }
    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.Base_Theme_Mecanica,
            { datePicker, year, month, day ->
                // Mostra o valor selecionado no EditText
                data.setText("$day/$month/$year")
            },
            2023, 1, 20
        )
        datePickerDialog.show()
        println("Fechou o DatePicker") // Adicione este log
    }
}
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


class CadastrarFuncionarioTela : AppCompatActivity() {
    private lateinit var vertudo: Button
    private lateinit var add: Button
    private lateinit var datacontrato: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_funcionario_tela)
        val database: SQLiteDatabase = openOrCreateDatabase("DB_USUARIOS", MODE_PRIVATE, null)

        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS funcionarios(nome VARCHAR, rg INT(10), contrato_data VARCHAR)")

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
            val allfuncionarios = Intent(this, SeeAllFuncionarios::class.java)
            startActivity(allfuncionarios)

        }

        add = findViewById(R.id.add)

        add.setOnClickListener {
            val nomefuncionaro: EditText = findViewById(R.id.nomefuncionario)
            val rgfuncionario: EditText = findViewById(R.id.rgfuncionario)

            val nomeUsuario = nomefuncionaro.text.toString()
            val rgUsuario = rgfuncionario.text.toString()
            val dataocontrato = datacontrato.text.toString()

            if(nomefuncionaro.text.isNotEmpty() && rgfuncionario.text.isNotEmpty() && datacontrato.text.isNotEmpty()){
                try {
                    // Use ? como marcadores de posição e passe os valores como argumentos para evitar SQL injection
                    database.execSQL("INSERT INTO funcionarios (nome, rg, contrato_data) VALUES (?, ?, ?)",
                        arrayOf(nomeUsuario, rgUsuario, dataocontrato))

                    // Consulta para verificar os dados inseridos
                    val cursor = database.rawQuery("SELECT * FROM funcionarios", null)

                    // Índices das colunas
                    val nomeindex = cursor.getColumnIndex("nome")
                    val rgUsuarioindex = cursor.getColumnIndex("rg")
                    val dataocontratoindex = cursor.getColumnIndex("contrato_data")

                    cursor.moveToFirst()

                    while (!cursor.isAfterLast) {
                        val nome = cursor.getString(nomeindex)
                        val rguser = cursor.getString(rgUsuarioindex)
                        val idadeuser = cursor.getString(dataocontratoindex)

                        Log.i("resultado", "/nome: $nome/ $rguser/ $idadeuser")
                        cursor.moveToNext()
                    }
                    nomefuncionaro.text.clear()
                    rgfuncionario.text.clear()
                    datacontrato.text = ""

                    cursor.moveToFirst()
                    Toast.makeText(this, "Funcionario adicionado com sucesso", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Erro ao adicionar funcionario", Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()

            }


        }

        datacontrato = findViewById(R.id.datacontrato)
        datacontrato.setOnClickListener {
            openDatePicker()
        }

    }

    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.Base_Theme_Mecanica,
            { datePicker, year, month, day ->
                // Mostra o valor selecionado no EditText
                datacontrato.setText("$day/$month/$year")
            },
            2023, 1, 20
        )
        datePickerDialog.show()
        println("Fechou o DatePicker") // Adicione este log
    }


}


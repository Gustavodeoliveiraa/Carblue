package com.example.mecanica

import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class seeAllClients : AppCompatActivity() {
    private lateinit var userTable: TableLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addclientebutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_clients)
        val database: SQLiteDatabase = openOrCreateDatabase("DB_USUARIOS", MODE_PRIVATE, null)

        userTable = findViewById(R.id.userTable)

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

        try {
            val cursor: Cursor = database.rawQuery("SELECT * FROM usuarios", null)

            // Índices das colunas
            val nameIndex = cursor.getColumnIndex("nome")
            val rgIndex = cursor.getColumnIndex("rg")
            val nascIndex = cursor.getColumnIndex("nascimento")

            while (cursor.moveToNext()) {
                val nome = cursor.getString(nameIndex)
                val rg = cursor.getString(rgIndex)
                val nascimento = cursor.getString(nascIndex)

                // Adicione dinamicamente uma nova linha à tabela
                addRowToTable(nome, rg, nascimento)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Método para adicionar dinamicamente uma nova linha à tabela
    private fun addRowToTable(nome: String, rg: String, nascimento: String) {
        val tableRow = TableRow(this)

        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        tableRow.layoutParams = params

        val editTextNome = createEditText(nome)
        tableRow.addView(editTextNome)

        val editTextRg = createEditText(rg)
        tableRow.addView(editTextRg)

        val editTextNascimento = createEditText(nascimento)
        tableRow.addView(editTextNascimento)

        // Adicione a nova linha à tabela
        userTable.addView(tableRow)

        // Adicione o efeito zebrado
        val index = userTable.indexOfChild(tableRow)
        if (index % 2 == 0) {
            // Linha par
            tableRow.setBackgroundColor(resources.getColor(R.color.corLinhaPar))
        } else {
            // Linha ímpar
            tableRow.setBackgroundColor(resources.getColor(R.color.corLinhaImpar))
        }
    }

    private fun createEditText(text: String): EditText {
        val editText = EditText(this)
        editText.text = Editable.Factory.getInstance().newEditable(text)

        // Remover a linha inferior
        editText.background = null
        editText.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)

        editText.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        editText.setPadding(35, 25, 0, 25)




        return editText
    }
}

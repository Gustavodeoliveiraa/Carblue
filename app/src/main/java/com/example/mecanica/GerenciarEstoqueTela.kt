package com.example.mecanica

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class GerenciarEstoqueTela : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userTable: TableLayout
    private lateinit var codPecaEditText: EditText
    private lateinit var pecaEditText: EditText
    private lateinit var quantidadeEditText: EditText
    private lateinit var valorEditText: EditText
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_estoque_tela)

        val database: SQLiteDatabase = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)

        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS estouque(cod VARCHAR, peca VARCHAR, quantidade INT, valor DECIMAL(10, 2))")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val adicionarpecatable = findViewById<Button>(R.id.adicionarpeca)
        adicionarpecatable.setOnClickListener {
            val formaddpeca = Intent(this, AddPecaTela::class.java)
            startActivity(formaddpeca)
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

        userTable = findViewById(R.id.userTable)
        codPecaEditText = findViewById(R.id.codpeca)
        pecaEditText = findViewById(R.id.peca)
        quantidadeEditText = findViewById(R.id.quantidade)
        valorEditText = findViewById(R.id.valor)
        searchView = findViewById(R.id.searchView)

        try {
            val cursor: Cursor = database.rawQuery("SELECT * FROM estouque", null)

            // Índices das colunas
            val codIndex = cursor.getColumnIndex("cod")
            val pecaIndex = cursor.getColumnIndex("peca")
            val quantidadeIndex = cursor.getColumnIndex("quantidade")
            val valorIndex = cursor.getColumnIndex("valor")

            while (cursor.moveToNext()) {
                val cod = cursor.getString(codIndex)
                val peca = cursor.getString(pecaIndex)
                val quantidade = cursor.getString(quantidadeIndex)
                val valor = cursor.getString(valorIndex)

                // Adicione dinamicamente uma nova linha à tabela
                addRowToTable(cod, peca, quantidade, valor)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addRowToTable(cod: String, peca: String, quantidade: String, valor: String) {
        val tableRow = TableRow(this)

        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        tableRow.layoutParams = params

        val codEditText = createEditText(cod)
        val pecaEditText = createEditText(peca)
        val quantidadeEditText = createEditText(quantidade)
        val valorEditText = createEditText(valor)

        tableRow.addView(codEditText)
        tableRow.addView(pecaEditText)
        tableRow.addView(quantidadeEditText)
        tableRow.addView(valorEditText)

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

    // Função auxiliar para criar um EditText dinamicamente
    private fun createEditText(text: String): EditText {
        val editText = EditText(this)
        editText.text = Editable.Factory.getInstance().newEditable(text)
        editText.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        return editText
    }

}

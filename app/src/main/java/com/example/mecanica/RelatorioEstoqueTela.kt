package com.example.mecanica

import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RelatorioEstoqueTela : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userTable: TableLayout
    private lateinit var codPecaEditText: EditText
    private lateinit var pecaEditText: EditText
    private lateinit var quantidadeEditText: EditText
    private lateinit var valorEditText: EditText
    private lateinit var searchView: SearchView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio_estoque_tela)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)

        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS estouque(cod VARCHAR, peca VARCHAR, quantidade INT, valor DECIMAL(10, 2))")
        } catch (e: Exception) {
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
                addFormattedRowToTable(cod, peca, quantidade, valor)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Configurar listener para a SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Chame a função para filtrar a tabela com base no nome da peça
                filterTableByName(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterTableByName(pecaName: String) {
        // Mantenha o cabeçalho e limpe apenas as linhas de dados
        val rowCount = userTable.childCount
        for (i in 1 until rowCount) {
            userTable.removeViewAt(1)
        }

        try {
            val cursor: Cursor = database.rawQuery("SELECT * FROM estouque WHERE peca LIKE ?", arrayOf("%$pecaName%"))

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

                // Adicione dinamicamente uma nova linha à tabela com formatação
                addFormattedRowToTable(cod, peca, quantidade, valor)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addFormattedRowToTable(cod: String, peca: String, quantidade: String, valor: String) {
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

        editText.background = null
        editText.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        editText.setPadding(35, 25, 0, 25)
        return editText
    }
}

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
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ControleProdutivadadeTela : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userTable: TableLayout
    private lateinit var codPecaEditText: EditText
    private lateinit var servicoEditText: EditText
    private lateinit var datadeEditText: EditText
    private lateinit var funcinarioEditText: EditText
    private lateinit var searchView: SearchView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controle_produtivadade_tela)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)

        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS produtividade02(cod VARCHAR, funcionario VARCHAR, data VARCHAR, servico VARCHAR)")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val adicionarpecatable = findViewById<Button>(R.id.aicionar)
        adicionarpecatable.setOnClickListener {
            val formaddpeca = Intent(this, AddConteoleProdutividade::class.java)
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
        funcinarioEditText = findViewById(R.id.funcionario)
        datadeEditText = findViewById(R.id.data)
        servicoEditText = findViewById(R.id.servico)

        searchView = findViewById(R.id.searchView)

        try {
            val cursor: Cursor = database.rawQuery("SELECT * FROM produtividade02", null)

            // Índices das colunas
            val codIndex = cursor.getColumnIndex("cod")
            val funcionarioIndex = cursor.getColumnIndex("funcionario")
            val dataIndex = cursor.getColumnIndex("data")
            val servicoIndex = cursor.getColumnIndex("servico")

            while (cursor.moveToNext()) {
                val cod = cursor.getString(codIndex)
                val funcionario = cursor.getString(funcionarioIndex)
                val data = cursor.getString(dataIndex)
                val servico = cursor.getString(servicoIndex)

                // Adicione dinamicamente uma nova linha à tabela
                addFormattedRowToTable(cod, funcionario, data, servico)
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

    override fun onResume() {
        super.onResume()
        refreshTableData()
    }

    private fun refreshTableData() {
        // Limpe a tabela antes de recarregar os dados
        clearTable()

        try {
            val cursor: Cursor = database.rawQuery("SELECT * FROM produtividade02", null)

            // Índices das colunas
            val codIndex = cursor.getColumnIndex("cod")
            val funcionarioIndex = cursor.getColumnIndex("funcionario")
            val dataIndex = cursor.getColumnIndex("data")
            val servicoIndex = cursor.getColumnIndex("servico")

            while (cursor.moveToNext()) {
                val cod = cursor.getString(codIndex)
                val funcionario = cursor.getString(funcionarioIndex)
                val data = cursor.getString(dataIndex)
                val servico = cursor.getString(servicoIndex)

                // Adicione dinamicamente uma nova linha à tabela
                addFormattedRowToTable(cod, funcionario, data, servico)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearTable() {
        // Mantenha o cabeçalho e limpe apenas as linhas de dados
        val rowCount = userTable.childCount
        for (i in 1 until rowCount) {
            userTable.removeViewAt(1)
        }
    }

    private fun addFormattedRowToTable(cod: String, funcionario: String, data: String, servico: String) {
        val tableRow = TableRow(this)

        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        tableRow.layoutParams = params

        val codEditText = createEditText(cod)
        val funcionarioEditText = createEditText(funcionario)
        val dataEditText = createEditText(data)
        val servicoEditText = createEditText(servico)

        tableRow.addView(codEditText)
        tableRow.addView(funcionarioEditText)
        tableRow.addView(dataEditText)
        tableRow.addView(servicoEditText)

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
        editText.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        editText.background = null
        editText.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        editText.setPadding(35, 25, 0, 25)

        return editText
    }

    private fun filterTableByName(pecaName: String) {
        clearTable()

        try {
            val cursor: Cursor = database.rawQuery(
                "SELECT * FROM produtividade02 WHERE funcionario LIKE ? OR data LIKE ?",
                arrayOf("%$pecaName%", "%$pecaName%")
            )

            val codIndex = cursor.getColumnIndex("cod")
            val funcionarioIndex = cursor.getColumnIndex("funcionario")
            val datadaIndex = cursor.getColumnIndex("data")
            val servicoIndex = cursor.getColumnIndex("servico")

            while (cursor.moveToNext()) {
                val cod = cursor.getString(codIndex)
                val funcionario = cursor.getString(funcionarioIndex)
                val data = cursor.getString(datadaIndex)
                val servico = cursor.getString(servicoIndex)

                addFormattedRowToTable(cod, funcionario, data, servico)
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

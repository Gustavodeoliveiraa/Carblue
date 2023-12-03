package com.example.mecanica

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddPecaTela : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var concluir: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_peca_tela)

        try {
            val database: SQLiteDatabase = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS estouque(cod VARCHAR, peca VARCHAR, quantidade INT, valor DECIMAL(10, 2))");

        }catch(e: Exception){
            e.printStackTrace()
        }

        val codEditText = findViewById<EditText>(R.id.Cod)
        val pecaEditText = findViewById<EditText>(R.id.peca)
        val quantidadeEditText = findViewById<EditText>(R.id.quantidade)
        val valorEditText = findViewById<EditText>(R.id.valor)

        concluir = findViewById(R.id.concluir)
        concluir.setOnClickListener {
            val codValue = codEditText.text.toString()
            val pecaValue = pecaEditText.text.toString()
            val quantidadeValue = quantidadeEditText.text.toString()
            val valorValue = valorEditText.text.toString()

            try {
                val database: SQLiteDatabase = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
                val query = "INSERT INTO estouque (cod, peca, quantidade, valor) VALUES ('$codValue', '$pecaValue', $quantidadeValue, $valorValue)"
                database.execSQL(query)

                Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show()


                // Lembre-se de fechar o banco de dados quando terminar
                database.close()
                codEditText.text.clear()
                pecaEditText.text.clear()
                valorEditText.text.clear()
                quantidadeEditText.text.clear()

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
}
package com.example.mecanica

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CriarAvaliacaoServico : AppCompatActivity() {
    private lateinit var database: SQLiteDatabase
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_avaliacao_servico)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
        database.execSQL("CREATE TABLE IF NOT EXISTS avaliar(nomecliente VARCHAR, nomeatendente VARCHAR, avaliacao VARCHAR)")

        val nomeCarEditText: EditText = findViewById(R.id.nomeavalicao)
        val atendenteEditText: EditText = findViewById(R.id.atendente)
        val avaliacaoEditText: EditText = findViewById(R.id.avaliacaocliente)


        val concluirButton: Button = findViewById(R.id.addavaliacao)

        concluirButton.setOnClickListener {
            try {
                val nomecliente = nomeCarEditText.text.toString()
                val atendente = atendenteEditText.text.toString()
                val avaliacaocliente = avaliacaoEditText.text.toString()

                // Inserir os valores diretamente sem usar uma função separada
                database.execSQL("CREATE TABLE IF NOT EXISTS avaliar(nomecliente VARCHAR, nomeatendente VARCHAR, avaliacao VARCHAR)")
                database.execSQL("INSERT INTO avaliar VALUES ('$nomecliente', '$atendente', '$avaliacaocliente')")

                // Limpar os EditTexts após a conclusão
                nomeCarEditText.text.clear()
                nomeCarEditText.text.clear()
                atendenteEditText.text.clear()
                avaliacaoEditText.text.clear()

                Toast.makeText(this, "Avaliação adicionada com sucesso", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                // Lidar com a exceção aqui, por exemplo, mostrar uma mensagem de erro
                Toast.makeText(this, "Erro ao adicionar avaliação", Toast.LENGTH_SHORT).show()
            }
        }

        val vertodas = findViewById<Button>(R.id.vertodosavaliacao)
        vertodas.setOnClickListener {
            val home = Intent(this, SeAllAvaliacoes::class.java)
            startActivity(home)
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

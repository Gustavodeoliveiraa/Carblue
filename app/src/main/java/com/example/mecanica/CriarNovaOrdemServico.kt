package com.example.mecanica

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CriarNovaOrdemServico : AppCompatActivity() {
    private lateinit var database: SQLiteDatabase
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_nova_ordem_servico)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)
        database.execSQL("CREATE TABLE IF NOT EXISTS ordemdeservico(carro VARCHAR, placa VARCHAR, mecanico VARCHAR, descricao_servico VARCHAR)")

        val nomeCarEditText: EditText = findViewById(R.id.nomecar)
        val placaEditText: EditText = findViewById(R.id.placa)
        val mecanicoEditText: EditText = findViewById(R.id.mecanico)
        val descricaoServicoEditText: EditText = findViewById(R.id.descricaoservico)
        val concluirButton: Button = findViewById(R.id.concluir)

        concluirButton.setOnClickListener {
            try {
                val carro = nomeCarEditText.text.toString()
                val placa = placaEditText.text.toString()
                val mecanico = mecanicoEditText.text.toString()
                val descricaoServico = descricaoServicoEditText.text.toString()

                // Inserir os valores diretamente sem usar uma função separada
                database.execSQL("INSERT INTO ordemdeservico (carro, placa, mecanico, descricao_servico) " +
                        "VALUES ('$carro', '$placa', '$mecanico', '$descricaoServico')")

                // Limpar os EditTexts após a conclusão
                nomeCarEditText.text.clear()
                placaEditText.text.clear()
                mecanicoEditText.text.clear()
                descricaoServicoEditText.text.clear()

                Toast.makeText(this, "Ordem de serviço criada com sucesso", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                // Lidar com a exceção aqui, por exemplo, mostrar uma mensagem de erro
                Toast.makeText(this, "Erro ao criar ordem de serviço", Toast.LENGTH_SHORT).show()
            }
        }

        val vertodas = findViewById<Button>(R.id.vertodas)
        vertodas.setOnClickListener {
            val home = Intent(this, SeeAllOrdem::class.java)
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

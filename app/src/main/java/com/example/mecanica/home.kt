package com.example.mecanica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class home : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addclientebutton: Button
    private lateinit var relatorioestoque: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        val gerEstoque = findViewById<Button>(R.id.gerestoque)
        gerEstoque.setOnClickListener {
            val gerenciarestoque = Intent(this, GerenciarEstoqueTela::class.java)
            startActivity(gerenciarestoque)
        }


//        addcliente
        addclientebutton = findViewById(R.id.addclienteclick)

        addclientebutton.setOnClickListener {
            val addcliente = Intent(this, addclientetela::class.java)
            startActivity(addcliente)
        }

        relatorioestoque = findViewById(R.id.estoque)

        relatorioestoque.setOnClickListener {
            val relatorio = Intent(this, RelatorioEstoqueTela::class.java)
            startActivity(relatorio)
        }

        val cadastrarVeiculo = findViewById<Button>(R.id.addcar)

        cadastrarVeiculo.setOnClickListener {
            val cadastrarVeiculo = Intent(this, CadastroVeiculoTela::class.java)
            startActivity(cadastrarVeiculo)
        }

        val addfuncionario = findViewById<Button>(R.id.addFuncionario)
        addfuncionario.setOnClickListener {
            val addfuncionario = Intent(this, CadastrarFuncionarioTela::class.java)
            startActivity(addfuncionario)
        }

        val controlecaixa = findViewById<Button>(R.id.controleCaixa)
        controlecaixa.setOnClickListener {
            val controlexcaixa = Intent(this, ControleCaixaTela::class.java)
            startActivity(controlexcaixa)
        }

        val controleprodutividade = findViewById<Button>(R.id.produtividade)
        controleprodutividade.setOnClickListener {
            val controleprodutividade = Intent(this, ControleProdutivadadeTela::class.java)
            startActivity(controleprodutividade)
        }

        val ordemserivoc = findViewById<Button>(R.id.descServico)
        ordemserivoc.setOnClickListener {
            val ordemserivoc = Intent(this, CriarNovaOrdemServico::class.java)
            startActivity(ordemserivoc)
        }

        val avalicao = findViewById<Button>(R.id.avaliacao)
        avalicao.setOnClickListener {
            val avalicao = Intent(this, CriarAvaliacaoServico::class.java)
            startActivity(avalicao)
        }

    }
}


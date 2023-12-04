package com.example.mecanica

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class CadastroVeiculoTela : AppCompatActivity() {
    private lateinit var vertudo: Button
    private lateinit var add: Button
    private lateinit var datanasc: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_veiculo_tela)


        val database: SQLiteDatabase = openOrCreateDatabase("DB_CARROS", MODE_PRIVATE, null)

        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS carros(modelo VARCHAR, placa VARCHAR, ano VARCHAR)")

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
            val vertudo = Intent(this, SeeAllCar::class.java)
            startActivity(vertudo)

        }

        add = findViewById(R.id.addcarro)

        add.setOnClickListener {
            val modelo: EditText = findViewById(R.id.nomecarroservico)
            val placa: EditText = findViewById(R.id.plcacarro)
            val ano: EditText = findViewById(R.id.anocarro)

            val modelocarro = modelo.text.toString()
            val placacarro = formatarPlaca(placa.text.toString())
            val anocarro = ano.text.toString()

            if(modelo.text.isNotEmpty() && placa.text.isNotEmpty() && ano.text.isNotEmpty()){
                try {
                    // Use ? como marcadores de posição e passe os valores como argumentos para evitar SQL injection


                    database.execSQL("INSERT INTO carros(modelo, placa, ano) VALUES (?, ?, ?)",
                        arrayOf(modelocarro, placacarro, anocarro))

                    // Consulta para verificar os dados inseridos
                    val cursor = database.rawQuery("SELECT * FROM carros", null)

                    // Índices das colunas
                    val nameIndex = cursor.getColumnIndex("modelo")
                    val rgIndex = cursor.getColumnIndex("placa")
                    val nascIndex = cursor.getColumnIndex("ano")

                    cursor.moveToFirst()

                    while (!cursor.isAfterLast) {
                        val modelocar = cursor.getString(nameIndex)
                        val placacar = cursor.getString(rgIndex)
                        val anocar = cursor.getString(nascIndex)

                        Log.i("resultado", "/nome: $modelocar/ $placacar/ $anocar")
                        cursor.moveToNext()
                    }
                    modelo.text.clear()
                    placa.text.clear()
                    ano.text.clear()

                    cursor.close()
                    Toast.makeText(this, "Carro adicionado com sucesso", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Erro ao adicionar carro", Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun formatarPlaca(placa: String): String {
        // Remover qualquer caractere não alfanumérico da placa
        val placaLimpa = placa.replace(Regex("[^A-Za-z0-9]"), "")

        // Verificar se é placa padrão antigo (3 letras, hífen, 4 números)
        if (placaLimpa.length == 7 && placaLimpa[4].isDigit()) {
            val placaFormatada = StringBuilder()

            for ((index, char) in placaLimpa.withIndex()) {
                placaFormatada.append(if (char.isLetter() && index < 3) char.toUpperCase() else char)
                if (index == 2) {
                    placaFormatada.append('-')
                }
            }

            return placaFormatada.toString()
        }

        // Se não corresponder ao padrão conhecido, retornar a placa original
        return placa.uppercase()
    }

}


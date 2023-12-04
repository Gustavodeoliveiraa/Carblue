package com.example.mecanica

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SeAllAvaliacoes : AppCompatActivity() {

    private lateinit var database: SQLiteDatabase
    private lateinit var linearLayout: LinearLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_se_all_avaliacoes)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)

        linearLayout = findViewById(R.id.linearLayoutCard)

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

        fetchDataAndDisplay()
    }

    private fun fetchDataAndDisplay() {
        val cursor: Cursor = database.rawQuery("SELECT * FROM avaliar", null)

        while (cursor.moveToNext()) {
            val nomeclienteIndex = cursor.getColumnIndex("nomecliente")
            val nomeatendenteIndex = cursor.getColumnIndex("nomeatendente")
            val avaliacaoIndex = cursor.getColumnIndex("avaliacao")

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Adiciona margem inferior aos cards, exceto para o último
            layoutParams.height = 300
            layoutParams.bottomMargin = 35 // Ajuste o valor conforme necessário

            val childLayout = LinearLayout(this)
            childLayout.orientation = LinearLayout.VERTICAL
            childLayout.layoutParams = layoutParams

            // Use o arquivo XML como fundo para o LinearLayout (cards)
            childLayout.background = ContextCompat.getDrawable(this, R.drawable.rounded_corners)

            // Layout horizontal para Nome Cliente e Nome Atendente
            val nomeAtendenteLayout = LinearLayout(this)
            nomeAtendenteLayout.orientation = LinearLayout.HORIZONTAL

            val nomeClienteTextView = TextView(this)
            nomeClienteTextView.text = "Cliente:" + cursor.getString(nomeclienteIndex)

            // Adiciona margens apenas ao nomeClienteTextView
            val nomeClienteParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            nomeClienteParams.setMargins(100, 15, 350, 25)
            nomeClienteTextView.layoutParams = nomeClienteParams

            nomeAtendenteLayout.addView(nomeClienteTextView)

            val atendenteTextView = TextView(this)
            atendenteTextView.text = "Atendente:" + cursor.getString(nomeatendenteIndex)

            // Adiciona margens apenas ao atendenteTextView
            val atendenteParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            atendenteParams.setMargins(0, 15, 0, 0)
            atendenteTextView.layoutParams = atendenteParams

            atendenteTextView.gravity = Gravity.END
            nomeAtendenteLayout.addView(atendenteTextView)

            childLayout.addView(nomeAtendenteLayout)

            val avaliacaoTextView = TextView(this)
            avaliacaoTextView.text = cursor.getString(avaliacaoIndex)

            // Adiciona margem ao avaliacaoTextView
            val avaliacaoParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            avaliacaoParams.setMargins(20, 20, 0, 0)
            avaliacaoTextView.layoutParams = avaliacaoParams

            childLayout.addView(avaliacaoTextView)

            linearLayout.addView(childLayout)
        }

        cursor.close()
    }
}

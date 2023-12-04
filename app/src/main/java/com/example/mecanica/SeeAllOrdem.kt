package com.example.mecanica

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SeeAllOrdem : AppCompatActivity() {

    private lateinit var database: SQLiteDatabase
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_ordem)

        database = openOrCreateDatabase("DB_ESTOQUE", MODE_PRIVATE, null)

        linearLayout = findViewById(R.id.linearLayoutCard)

        // Fetch data from the database and create TextViews dynamically
        fetchDataAndDisplay()
    }

    private fun fetchDataAndDisplay() {
        val cursor: Cursor = database.rawQuery("SELECT * FROM ordemdeservico", null)

        while (cursor.moveToNext()) {
            val carroIndex = cursor.getColumnIndex("carro")
            val placaIndex = cursor.getColumnIndex("placa")
            val mecanicoIndex = cursor.getColumnIndex("mecanico")
            val descservIndex = cursor.getColumnIndex("descricao_servico")

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Adiciona margem inferior aos cards, exceto para o último
            layoutParams.bottomMargin = 16 // Ajuste o valor conforme necessário

            val childLayout = LinearLayout(this)
            childLayout.orientation = LinearLayout.VERTICAL
            childLayout.layoutParams = layoutParams
            childLayout.setBackgroundResource(R.color.odemservico)

            // Layout horizontal para Carro e Placa
            val carPlacaLayout = LinearLayout(this)
            carPlacaLayout.orientation = LinearLayout.HORIZONTAL

            // Adiciona margem ao topo de carPlacaLayout
            val carPlacaLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            carPlacaLayoutParams.topMargin = 2
            carPlacaLayout.layoutParams = carPlacaLayoutParams

            carPlacaLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val carTextView = TextView(this)
            carTextView.text = cursor.getString(carroIndex)

            // Adiciona margens apenas ao carTextView
            val carTextViewParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            carTextViewParams.setMargins(100, 15, 500, 25)
            carTextView.layoutParams = carTextViewParams

            carPlacaLayout.addView(carTextView)

            val placaTextView = TextView(this)
            placaTextView.text = cursor.getString(placaIndex)
            placaTextView.gravity = Gravity.END

            // Adiciona margens apenas ao placaTextView
            val placaTextViewParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            placaTextViewParams.setMargins(100, 15, 0, 0)
            placaTextView.layoutParams = placaTextViewParams

            carPlacaLayout.addView(placaTextView)

            childLayout.addView(carPlacaLayout)

            val mecanicoTextView = TextView(this)
            mecanicoTextView.text = cursor.getString(mecanicoIndex)
            mecanicoTextView.gravity = Gravity.CENTER_HORIZONTAL

            childLayout.addView(mecanicoTextView)

            val descricaoTextView = TextView(this)
            descricaoTextView.text = cursor.getString(descservIndex)

            // Adiciona margem à esquerda de descricaoTextView
            val descricaoTextViewParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            descricaoTextViewParams.setMargins(50, 30, 0, 30) // Ajuste o valor conforme necessário
            descricaoTextView.layoutParams = descricaoTextViewParams

            childLayout.addView(descricaoTextView)

            linearLayout.addView(childLayout)
        }

        cursor.close()
    }
}
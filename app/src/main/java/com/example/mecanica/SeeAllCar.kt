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
import com.google.android.material.bottomnavigation.BottomNavigationView

class SeeAllCar : AppCompatActivity() {
    private lateinit var userTable: TableLayout
    private lateinit var database: SQLiteDatabase
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_car)

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

        database = openOrCreateDatabase("DB_CARROS", MODE_PRIVATE, null)

        try {
            database.execSQL("")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        userTable = findViewById(R.id.userTable)

        val cursor: Cursor = database.rawQuery("SELECT * FROM carros", null)

        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex("modelo")
            val rgIndex = cursor.getColumnIndex("placa")
            val nascIndex = cursor.getColumnIndex("ano")

            do {
                val modelo = cursor.getString(nameIndex)
                val placa = cursor.getString(rgIndex)
                val ano = cursor.getString(nascIndex)
                addRowToTable(modelo, placa, ano)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun addRowToTable(modelo: String, placa: String, ano: String) {
        val tableRow = TableRow(this)

        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        tableRow.layoutParams = params

        val editTextModelo = createEditText(modelo)
        val editTextPlaca = createEditText(placa)
        val editTextAno = createEditText(ano)

        tableRow.addView(editTextModelo)
        tableRow.addView(editTextPlaca)
        tableRow.addView(editTextAno)

        userTable.addView(tableRow)

        val index = userTable.indexOfChild(tableRow)
        if (index % 2 == 0) {
            tableRow.setBackgroundColor(resources.getColor(R.color.corLinhaPar))
        } else {
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
}

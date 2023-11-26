package com.example.mecanica

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private lateinit var buttonlogin: Button;
    private lateinit var buttonregister: Button;
    private lateinit var textlogin: EditText;
    private lateinit var textpassword: EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth =  Firebase.auth


        buttonlogin = findViewById<Button>(R.id.loginbutton)

        buttonlogin.setOnClickListener {

            textlogin = findViewById(R.id.usertext)
            textpassword = findViewById(R.id.userpassword)

            val user: String = textlogin.text.toString()
            val password: String = textpassword.text.toString()

            if(user.isNotEmpty() && password.isNotEmpty()){
                singInWithEmailAndPassword(user, password)
                val home = Intent(this, home::class.java)
                startActivity(home)
            }else{
                Toast.makeText(this@MainActivity, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }

        buttonregister = findViewById<Button>(R.id.registebutton)


        buttonregister.setOnClickListener {
            println("registro")
            textlogin = findViewById(R.id.usertext)
            textpassword = findViewById(R.id.userpassword)

            val user: String = textlogin.text.toString()
            val password: String = textpassword.text.toString()

            if(user.isNotEmpty() && password.isNotEmpty()){
                createUserWithEmailAndPassword(user, password)
                Toast.makeText(this@MainActivity, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this@MainActivity, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUserWithEmailAndPassword(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Log.d(TAG, "createUserWithEmailAndPassword:Success")
                val user = auth.currentUser
                Toast.makeText(baseContext, "Register successfully", Toast.LENGTH_SHORT).show()

            }else {
                Log.w(TAG, "createUserWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun singInWithEmailAndPassword(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Log.d(TAG, "singInWithEmailAndPassword:Success")
                val user = auth.currentUser
            }else{
                Log.w(TAG, "singInWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext, "Authentication Failure", Toast.LENGTH_SHORT).show()

            }
        }
    }
    companion object{
        private var TAG = "emailAndPassword"
    }
}
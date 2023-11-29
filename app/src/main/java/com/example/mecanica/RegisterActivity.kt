package com.example.mecanica

import android.app.Activity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonregister: Button;
    private lateinit var textlogin: EditText;
    private lateinit var textpassword: EditText;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth =  Firebase.auth


    }



}
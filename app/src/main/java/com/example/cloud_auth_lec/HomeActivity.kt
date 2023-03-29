package com.example.cloud_auth_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var email = intent.getStringExtra("email")
        var id = intent.getStringExtra("id")

        emailText.text = email
        idText.text = id

        buttonLogOut.setOnClickListener{
            //log out code
            Firebase.auth.signOut()
            updateUI()
        }
    }

    fun updateUI() {
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }
}
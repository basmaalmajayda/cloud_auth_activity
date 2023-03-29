package com.example.cloud_auth_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser!!.email!!, currentUser.uid)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        var email = emailLogin.text
        var password = passwordLogin.text

        buttonLogin.setOnClickListener{
            if(email.toString().isEmpty()){
                Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.toString().isEmpty()){
                Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //login code
            login(email.toString(), password.toString())
            }

        signLogin.setOnClickListener{
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("basma", "loginWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user!!.email!!, user.uid)
                } else {
                    Log.w("basma", "loginWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun updateUI(email: String, id: String) {
        var i = Intent(this, HomeActivity::class.java)
        i.putExtra("email", email)
        i.putExtra("id", id)
        startActivity(i)
    }

}
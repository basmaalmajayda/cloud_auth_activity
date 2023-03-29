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
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        var email = emailSignUp.text
        var password = passwordSignUp.text
        var confirmePassword = confirmePasswordSignUp.text

        buttonSignUp.setOnClickListener{
            if(email.toString().isEmpty()){
                Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.toString().isEmpty()){
                Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(confirmePassword.toString().isEmpty()){
                Toast.makeText(this, "Please enter password again!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!confirmePassword.toString().equals(password.toString())){
                Toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //sign up code
            createAccount(email.toString(), password.toString())
            }

        loginSignUp.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("basma", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user!!.email!!, user.uid)
                } else {
                    Log.w("basma", "createUserWithEmail:failure", task.exception)
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
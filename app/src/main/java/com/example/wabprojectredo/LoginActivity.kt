package com.example.wabprojectredo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_main_login.setOnClickListener {
            performLogin()
        }

        btn_main_newUser.setOnClickListener {
            Log.d("LoginActivity", "Try to show register activity")

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(){
        val enteredLoginEmail = txt_main_emailEntry.text.toString()
        val enteredLoginPassword = txt_main_passwordEntry.text.toString()

        if (enteredLoginEmail.isEmpty() || enteredLoginPassword.isEmpty()){
            Toast.makeText(this, "Please enter an email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("LoginActivity", "Email is: " + enteredLoginEmail)
        Log.d("LoginActivity", "Password is: $enteredLoginPassword")


        FirebaseAuth.getInstance().signInWithEmailAndPassword(enteredLoginEmail, enteredLoginPassword)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful -- only log user in if their email is verified
                if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                    Log.d("Main", "Successfully logged in user with uid: ${it.result?.user?.uid}")
                    Toast.makeText(this, "Successfully logged in. Welcome!", Toast.LENGTH_SHORT).show()

                    //sends app to the homepage after login
                    val intent = Intent(this, HomepageActivity::class.java)
                    //clears the login page so the user can't go back to it
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Please verify your email address in order to log in."
                        , Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
            }
            .addOnFailureListener{
                Log.d("Register", "Failed to login user: ${it.message}")
                Toast.makeText(this, "Failed to login user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

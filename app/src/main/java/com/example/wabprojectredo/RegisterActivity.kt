package com.example.wabprojectredo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wabprojectredo.classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = "Register"

        btn_register_register.setOnClickListener {
            performRegister()
            //TODO: MAKE SURE USERNAMES ARE UNIQUE. this is automatically done with emails
        }

        btn_register_backToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun performRegister(){
        val enteredEmail = txt_register_emailEntry.text.toString()
        val enteredPassword = txt_register_passwordConfirm.text.toString()

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()){
            Toast.makeText(this, "Please enter an email and password.", Toast.LENGTH_SHORT).show()
            return
        }
        // TODO else if (!enteredEmail.contains("bhm.k12.al.us")){
        //Toast.makeText(this, "Email must be for the birmingham school district.", Toast.LENGTH_SHORT).show()
        //return
        //}

        Log.d("LoginActivity", "Email is: $enteredEmail")
        Log.d("LoginActivity", "Password is: $enteredPassword")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(enteredEmail, enteredPassword)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                saveUserToFirebaseDatabase()
                //else if successful
                Log.d("Register", "Successfully created user with uid: ${it.result?.user?.uid}")
                //send email to verify user
                //TODO: uncomment below code and test with ms. bieri's email
                FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        Log.d("Register", "Sent email verificaiton email.")
                        Toast.makeText(this, "Verification email has been sent to your inbox. Click the" +
                                " link in the email to be able to log in.", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener{
                        Log.d("Register", "Verification email sending failed.")
                        Toast.makeText(this, "Error sending verification email. Please try creating " +
                                "your account again.", Toast.LENGTH_SHORT).show()
                    }

            }
            .addOnFailureListener{
                Log.d("Register", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun saveUserToFirebaseDatabase(){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, txt_register_usernameEntry.text.toString(), txt_register_emailEntry.text.toString())

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "Saved user to firebase database")
                val intent = Intent(this, LoginActivity::class.java)
                //take user to login screen and clear out unused activities from the stack
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("Register", "Failed to save user to database: ${it.message}")
            }
    }
}



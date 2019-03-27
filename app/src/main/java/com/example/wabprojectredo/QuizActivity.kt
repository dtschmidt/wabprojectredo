package com.example.wabprojectredo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wabprojectredo.classes.ChatMessage
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
    }

    /*private fun listenForQuestions(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages/ChatRoom1")

      ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val questionItem = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(ChatRoom1Activity.TAG, chatMessage?.text)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }*/
}

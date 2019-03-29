package com.example.wabprojectredo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wabprojectredo.classes.Question
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_games.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GamesActivity : AppCompatActivity() {

    companion object {
        var questionArray = arrayOf<Question>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        //TODO: listen for questions needs to run all the way through before adding the extra.
        btn_games_quiz.setOnClickListener {
            listenForQuestions()
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("questionArray", questionArray)
            startActivity(intent)
        }
    }

    private fun listenForQuestions(){


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM")
        val formatted = current.format(formatter)
        val currentMonth = QuizActivity.months[formatted.toInt()]

        val ref = FirebaseDatabase.getInstance().getReference("/quizzes/$currentMonth")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val questionItem = p0.getValue(Question::class.java)

                if (questionItem != null) {
                    questionArray += questionItem

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
    }
}

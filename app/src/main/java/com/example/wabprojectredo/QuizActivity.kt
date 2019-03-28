package com.example.wabprojectredo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wabprojectredo.classes.ChatMessage
import com.example.wabprojectredo.classes.Question
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_quiz.*
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class QuizActivity : AppCompatActivity() {

    companion object {
        /*used to get current month in listenForQuestions; first place is null so that
        1 corresponds with jan, 2 with feb, so on*/
        val months = arrayOf("null", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")

        var questionArray = arrayOf<Question>()

        var questionNumber = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)



        listenForQuestions()

        //TODO: note that an array starts at 0, need to fix
        //TODO: this while loop goes through real quick and doesnt actually do anything
        while (questionNumber < questionArray.size){
            Log.d("QuizActivity", "um hi")
            txtview_quiz_questionnum.text = "Question $questionNumber"
            txtview_quiz_qtext.text = questionArray[questionNumber].actualQuestion

            radio_quiz_1.text = questionArray[questionNumber].optionOne
            radio_quiz_2.text = questionArray[questionNumber].optionTwo
            radio_quiz_3.text = questionArray[questionNumber].optionThree
            radio_quiz_4.text = questionArray[questionNumber].optionFour

            //TODO: do something if nothing is checked
            btn_quiz_submitnext.setOnClickListener {

            }
        }
    }

    private fun listenForQuestions(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM")
        val formatted = current.format(formatter)
        val currentMonth = months[formatted.toInt()]

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

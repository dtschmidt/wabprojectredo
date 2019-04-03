package com.example.wabprojectredo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wabprojectredo.classes.ChatMessage
import com.example.wabprojectredo.classes.Question
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class QuizActivity : AppCompatActivity() {

    interface Callback {
        fun onComplete() {

        }
    }

    companion object {

        var questionArray = arrayOf<Question>()

        /*used to get current month in listenForQuestions; first place is null so that
        1 corresponds with jan, 2 with feb, so on*/
        val months = arrayOf("null", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")

        var questionNumber = 1

        var questionsCorrect = 0

        var totalQuestions = 0

        var submittedYet = false

        var doneCollecting = false

        var isSomethingChecked = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        //val questionArray = intent.getParcelableArrayExtra("questionArray")

        listenForQuestions()

        btn_quiz_1.setOnClickListener {
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            isSomethingChecked = true

            if (btn_quiz_1.text != questionArray[questionNumber].correctAnswer){
                highlightCorrectAnswer()
                btn_quiz_1.setBackgroundColor(Color.RED)
                btn_quiz_submitnext.text = "NEXT"
            }
            else if (btn_quiz_1.text == questionArray[questionNumber].correctAnswer){
                questionsCorrect++
                btn_quiz_1.setBackgroundColor(Color.GREEN)
                btn_quiz_submitnext.text = "NEXT"
            }
        }

        btn_quiz_2.setOnClickListener {

        }

        btn_quiz_3.setOnClickListener {

        }

        btn_quiz_4.setOnClickListener {

        }
        btn_quiz_submitnext.setOnClickListener {
            if (isSomethingChecked) {
                questionNumber++
                aaa()
            }
            else if (!isSomethingChecked){
                Toast.makeText(this, "Please make a selection.", Toast.LENGTH_SHORT).show()
            }
        }
        //TODO: note that an array starts at 0, need to fix
    }



     private fun aaa() {
        totalQuestions = questionArray.size
        if (questionNumber < questionArray.size){
            setUpPage()
            Log.d("quiz", "uhhhh")

        }
    }

    private fun setUpPage() {
        isSomethingChecked = false

        //set buttons to default colors and configurations - this is required when every new question shows up
        btn_quiz_1.setBackgroundColor(Color.RED)
        txtview_quiz_questionnum.text = "Question $questionNumber"
        txtview_quiz_qtext.text = questionArray[questionNumber].actualQuestion

        btn_quiz_1.text = questionArray[questionNumber].optionOne
        btn_quiz_2.text = questionArray[questionNumber].optionTwo
        btn_quiz_3.text = questionArray[questionNumber].optionThree
        btn_quiz_4.text = questionArray[questionNumber].optionFour
    }

    //also changes color of submit button
    /*private fun highlightCurrentAnswerIfWrong() {
        if (radio_quiz_1.isChecked && radio_quiz_1.text != questionArray[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_1.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_2.isChecked && radio_quiz_2.text != questionArray[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_2.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_3.isChecked && radio_quiz_3.text != questionArray[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_3.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_4.isChecked && radio_quiz_4.text != questionArray[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_4.setBackgroundColor(Color.RED)
        }
    }*/

    private fun highlightCorrectAnswer() {
        if (btn_quiz_1.text == questionArray[questionNumber].correctAnswer){
            btn_quiz_1.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_2.text == questionArray[questionNumber].correctAnswer){
            btn_quiz_2.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_3.text == questionArray[questionNumber].correctAnswer){
            btn_quiz_3.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_4.text == questionArray[questionNumber].correctAnswer){
            btn_quiz_4.setBackgroundColor(Color.GREEN)
        }
    }

    fun listenForQuestions(){

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM")
        val formatted = current.format(formatter)
        val currentMonth = QuizActivity.months[formatted.toInt()]
        var count = 0
        var numberOfQuestions = 0
        val ref = FirebaseDatabase.getInstance().getReference("/quizzes/$currentMonth")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val questionItem = p0.getValue(Question::class.java)

                //adding the individual questions to the array of questions
                if (questionItem != null) {
                    questionArray += questionItem
                    count++

                    //after all of the questions for this node are gathered, do this
                    if (count >= 10) {
                        doneCollecting = true
                        aaa()
                    }

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
        return
    }


}

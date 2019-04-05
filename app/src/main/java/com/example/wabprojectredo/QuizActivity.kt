package com.example.wabprojectredo

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.wabprojectredo.classes.ChatMessage
import com.example.wabprojectredo.classes.Question
import com.google.firebase.auth.FirebaseAuth
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

    companion object {

        var questionArray = emptyArray<Question>() //arrayOf<Question>()

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

        supportActionBar?.title = "Quiz"

        resetVaiables()
        listenForQuestions()

        btn_quiz_1.setOnClickListener {
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            isSomethingChecked = true

            if (btn_quiz_1.text != questionArray[questionNumber - 1].correctAnswer){
                highlightCorrectAnswer()
                btn_quiz_1.setBackgroundColor(Color.RED)
                btn_quiz_submitnext.text = "NEXT"
            }
            else if (btn_quiz_1.text == questionArray[questionNumber - 1].correctAnswer){
                questionsCorrect++
                btn_quiz_1.setBackgroundColor(Color.GREEN)
                btn_quiz_submitnext.text = "NEXT"
            }
        }

        btn_quiz_2.setOnClickListener {
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            isSomethingChecked = true

            if (btn_quiz_2.text != questionArray[questionNumber - 1].correctAnswer){
                highlightCorrectAnswer()
                btn_quiz_2.setBackgroundColor(Color.RED)
                btn_quiz_submitnext.text = "NEXT"
            }
            else if (btn_quiz_2.text == questionArray[questionNumber - 1].correctAnswer){
                questionsCorrect++
                btn_quiz_2.setBackgroundColor(Color.GREEN)
                btn_quiz_submitnext.text = "NEXT"
            }
        }

        btn_quiz_3.setOnClickListener {
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            isSomethingChecked = true

            if (btn_quiz_3.text != questionArray[questionNumber - 1].correctAnswer){
                highlightCorrectAnswer()
                btn_quiz_3.setBackgroundColor(Color.RED)
                btn_quiz_submitnext.text = "NEXT"
            }
            else if (btn_quiz_3.text == questionArray[questionNumber - 1].correctAnswer){
                questionsCorrect++
                btn_quiz_3.setBackgroundColor(Color.GREEN)
                btn_quiz_submitnext.text = "NEXT"
            }
        }

        btn_quiz_4.setOnClickListener {
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            isSomethingChecked = true

            if (btn_quiz_4.text != questionArray[questionNumber - 1].correctAnswer){
                highlightCorrectAnswer()
                btn_quiz_4.setBackgroundColor(Color.RED)
                btn_quiz_submitnext.text = "NEXT"
            }
            else if (btn_quiz_4.text == questionArray[questionNumber - 1].correctAnswer){
                questionsCorrect++
                btn_quiz_4.setBackgroundColor(Color.GREEN)
                btn_quiz_submitnext.text = "NEXT"
            }
        }
        btn_quiz_submitnext.setOnClickListener {
            if (isSomethingChecked && questionNumber < questionArray.size) {
                questionNumber++
                aaa()
            }

            //if all questions have been answered, finish the quiz
            else if (isSomethingChecked && questionNumber >= questionArray.size){
                val intent = Intent(this, FinishQuizActivity::class.java)
                startActivity(intent)
            }

            else if (!isSomethingChecked){
                Toast.makeText(this, "Please make a selection.", Toast.LENGTH_SHORT).show()
            }
        }
        //TODO: note that an array starts at 0, need to fix
    }

    private fun resetVaiables() {
        val emptyQuestionArray = emptyArray<Question>()
        questionArray = emptyQuestionArray

        questionNumber = 1
        questionsCorrect = 0
        totalQuestions = 0
        submittedYet = false
        doneCollecting = false
        isSomethingChecked = false
    }

     private fun aaa() {
        totalQuestions = questionArray.size
        if ((questionNumber - 1) < questionArray.size){
            setUpPage()
            Log.d("QuizActivity", "Page set up")
        }
    }

    private fun setUpPage() {
        resetButtonColors()
        isSomethingChecked = false

        //set buttons to default colors and configurations - this is required when every new question shows up

        txtview_quiz_questionnum.text = "Question $questionNumber)"
        txtview_quiz_questionnum3.text = "$questionNumber / ${questionArray.size}"
        txtview_quiz_qtext.text = questionArray[questionNumber - 1].actualQuestion

        btn_quiz_1.text = questionArray[questionNumber - 1].optionOne
        btn_quiz_2.text = questionArray[questionNumber - 1].optionTwo
        btn_quiz_3.text = questionArray[questionNumber - 1].optionThree
        btn_quiz_4.text = questionArray[questionNumber - 1].optionFour
    }

    private fun highlightCorrectAnswer() {
        if (btn_quiz_1.text == questionArray[questionNumber - 1].correctAnswer){
            btn_quiz_1.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_2.text == questionArray[questionNumber - 1].correctAnswer){
            btn_quiz_2.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_3.text == questionArray[questionNumber - 1].correctAnswer){
            btn_quiz_3.setBackgroundColor(Color.GREEN)
        }
        else if (btn_quiz_4.text == questionArray[questionNumber - 1].correctAnswer){
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

    private fun resetButtonColors() {
        btn_quiz_1.setBackgroundResource(android.R.drawable.btn_default)
        btn_quiz_2.setBackgroundResource(android.R.drawable.btn_default)
        btn_quiz_3.setBackgroundResource(android.R.drawable.btn_default)
        btn_quiz_4.setBackgroundResource(android.R.drawable.btn_default)
        btn_quiz_submitnext.setBackgroundResource(android.R.drawable.btn_default)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.menu_report -> {
                val intent = Intent(this, ReportActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

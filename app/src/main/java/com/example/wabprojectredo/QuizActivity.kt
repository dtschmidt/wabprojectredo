package com.example.wabprojectredo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wabprojectredo.GamesActivity.Companion.questionArray
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

        var questionNumber = 1

        var questionsCorrect = 0

        var totalQuestions = 0

        var submittedYet = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val questionArray = intent.getParcelableArrayExtra("questionArray")


   //     totalQuestions = questionList.size
  //      aaa()
        //TODO: note that an array starts at 0, need to fix
        //TODO: this while loop goes through real quick and doesnt actually do anything
    }
/*
    private fun aaa() {
        if (questionNumber < questionList.size){
            setUpPage()
            Log.d("quiz", "uhhhh")

            if (submittedYet == false) {
                btn_quiz_submitnext.setOnClickListener {
                    //plays only if user has not submitted their answer for the current question yet

                    highlightCorrectAnswer()
                    highlightCurrentAnswerIfWrong()

                    if (radio_quiz_1.isChecked && radio_quiz_1.text == questionList[questionNumber].correctAnswer) {
                        questionsCorrect++
                        submittedYet = true
                    } else if (radio_quiz_2.isChecked && radio_quiz_2.text == questionList[questionNumber].correctAnswer) {
                        questionsCorrect++
                        submittedYet = true
                    } else if (radio_quiz_3.isChecked && radio_quiz_3.text == questionList[questionNumber].correctAnswer) {
                        questionsCorrect++
                        submittedYet = true
                    } else if (radio_quiz_4.isChecked && radio_quiz_4.text == questionList[questionNumber].correctAnswer) {
                        questionsCorrect++
                        submittedYet = true
                    } else {
                        Toast.makeText(this, "Please make a selection.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }

            if (submittedYet == true) {
                btn_quiz_submitnext.setOnClickListener {
                    questionNumber++
                    aaa()
                }
            }
        }
    }

    private fun setUpPage() {
        txtview_quiz_questionnum.text = "Question $questionNumber"
        txtview_quiz_qtext.text = questionList[questionNumber].actualQuestion

        radio_quiz_1.text = questionList[questionNumber].optionOne
        radio_quiz_2.text = questionList[questionNumber].optionTwo
        radio_quiz_3.text = questionList[questionNumber].optionThree
        radio_quiz_4.text = questionList[questionNumber].optionFour
    }

    //also changes color of submit button
    private fun highlightCurrentAnswerIfWrong() {
        if (radio_quiz_1.isChecked && radio_quiz_1.text != questionList[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_1.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_2.isChecked && radio_quiz_2.text != questionList[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_2.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_3.isChecked && radio_quiz_3.text != questionList[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_3.setBackgroundColor(Color.RED)
        }
        else if (radio_quiz_4.isChecked && radio_quiz_4.text != questionList[questionNumber].correctAnswer){
            btn_quiz_submitnext.setBackgroundColor(Color.BLUE)
            radio_quiz_4.setBackgroundColor(Color.RED)
        }
    }

    private fun highlightCorrectAnswer() {
        if (radio_quiz_1.text == questionList[questionNumber].correctAnswer){
            radio_quiz_1.setBackgroundColor(Color.GREEN)
        }
        else if (radio_quiz_2.text == questionList[questionNumber].correctAnswer){
            radio_quiz_2.setBackgroundColor(Color.GREEN)
        }
        else if (radio_quiz_3.text == questionList[questionNumber].correctAnswer){
            radio_quiz_3.setBackgroundColor(Color.GREEN)
        }
        else if (radio_quiz_4.text == questionList[questionNumber].correctAnswer){
            radio_quiz_4.setBackgroundColor(Color.GREEN)
        }
    }
*/

}

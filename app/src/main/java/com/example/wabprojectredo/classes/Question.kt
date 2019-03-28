package com.example.wabprojectredo.classes

class Question(val actualQuestion:String,
            val optionOne:String,
           val optionTwo:String,
           val optionThree:String?,
           val optionFour:String?,
           val correctAnswer:String){

    constructor() : this("","","","","", "")
}
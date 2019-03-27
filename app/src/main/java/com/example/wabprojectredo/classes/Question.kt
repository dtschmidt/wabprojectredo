package com.example.wabprojectredo.classes

class Quiz(val answer1:Answer?,
           val answer2:Answer?,
           val answer3:Answer?,
           val answer4:Answer?,
           val text:String){

    constructor() : this(null,null,null,null,"")
}
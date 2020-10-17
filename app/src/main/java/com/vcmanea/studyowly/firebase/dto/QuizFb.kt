package com.vcmanea.studyowly.firebase.dto

class QuizFb(val id: Long = 0, val chapter_id: Long = 0, val question: String = "", val multiple_choice: Boolean = false)

class QuizChoicesFb(val id: Long = 0, val quiz_id: Long = 0, val choice: String = "", val isCorrect: Boolean = false)
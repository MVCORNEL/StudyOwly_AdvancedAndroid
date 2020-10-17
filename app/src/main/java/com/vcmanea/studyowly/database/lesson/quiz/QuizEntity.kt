package com.vcmanea.studyowly.database.lesson.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vcmanea.studyowly.domain.tutorial.quiz.MultipleChoiceQuiz
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.SingleChoiceQuiz
import com.vcmanea.studyowly.firebase.dto.QuizFb


@Entity
data class QuizEntity constructor(
    @PrimaryKey
    val id: Long,
    val chapter_id: Long,
    val question: String,
    val multiple_choice: Boolean
)


fun List<QuizEntity>.asQuizDomainModel(): List<Quiz> {
    return map {
        if (it.multiple_choice) {
            MultipleChoiceQuiz(
                id = it.id,
                chapter_id = it.chapter_id,
                question = it.question
            )
        }
        else {
           SingleChoiceQuiz(
                id = it.id,
                chapter_id = it.chapter_id,
                question = it.question
            )
        }
    }
}

fun ArrayList<QuizFb>.asDatabaseModel(): Array<QuizEntity> {
    return map {
        QuizEntity(
            id = it.id,
            chapter_id = it.chapter_id,
            question = it.question,
            multiple_choice = it.multiple_choice
        )
    }.toTypedArray()
}


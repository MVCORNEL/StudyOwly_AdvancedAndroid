package com.vcmanea.studyowly.database.lesson.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.firebase.dto.QuizChoicesFb


@Entity
data class ChoiceEntity constructor(
    @PrimaryKey
    val id: Long,
    val quiz_id: Long,
    val choice: String,
    val isCorrect: Boolean
)

fun List<ChoiceEntity>.asDomainModel(): List<Quiz.Choice> {
    return map {
        Quiz.Choice(
            id = it.id,
            quiz_id = it.quiz_id,
            choice = it.choice,
            isCorrect = it.isCorrect
        )
    }
}

fun ArrayList<QuizChoicesFb>.asDatabaseModel(): Array<ChoiceEntity> {
    return map {
        ChoiceEntity(
            id = it.id,
            quiz_id = it.quiz_id,
            choice = it.choice,
            isCorrect = it.isCorrect
        )
    }.toTypedArray()
}
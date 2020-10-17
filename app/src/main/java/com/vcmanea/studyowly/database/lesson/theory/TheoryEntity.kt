package com.vcmanea.studyowly.database.lesson.theory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vcmanea.studyowly.domain.tutorial.theory.Theory
import com.vcmanea.studyowly.firebase.dto.TheoryFb


@Entity
data class TheoryEntity constructor(
    @PrimaryKey
    val id:Long,
    val chapter_id:Long,
)

fun List<TheoryEntity>.asDomainModel():List<Theory>{
    return map{
        Theory(
            id = it.id,
            chapter_id = it.chapter_id

        )
    }
}

fun ArrayList<TheoryFb>.asDatabaseModel(): Array<TheoryEntity> {
    return map {
        TheoryEntity (
            id = it.id,
            chapter_id = it.chapter_id)
    }.toTypedArray()
}
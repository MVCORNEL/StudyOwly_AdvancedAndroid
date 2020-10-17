package com.vcmanea.studyowly.database.lesson.theory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vcmanea.studyowly.domain.tutorial.theory.TheoryPart
import com.vcmanea.studyowly.firebase.dto.TheoryPartFb


@Entity
data class TheoryPartEntity constructor(
    @PrimaryKey
    val id:Long,
    val theory_id: Long,
    val type: String,
    val content: String,
)

fun List<TheoryPartEntity>.asDomainModel(): List<TheoryPart> {
    return map {
        TheoryPart(
            theory_id = it.theory_id,
            type = it.type,
            content = it.content,
        )
    }
}

fun ArrayList<TheoryPartFb>.asDatabaseModel(): Array<TheoryPartEntity> {
    return map {
        TheoryPartEntity(
            id=it.id,
            theory_id = it.theory_id,
            type = it.type,
            content = it.content
        )
    }.toTypedArray()
}
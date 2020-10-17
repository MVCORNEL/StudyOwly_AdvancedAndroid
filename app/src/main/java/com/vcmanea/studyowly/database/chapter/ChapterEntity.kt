package com.vcmanea.studyowly.database.chapter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vcmanea.studyowly.domain.chapter.Chapter
import com.vcmanea.studyowly.firebase.dto.ChapterFb


//CHAPTER
@Entity
data class ChapterEntity constructor(
    @PrimaryKey
    val id:Long,
    @ColumnInfo(name="title")
    val title:String,
    @ColumnInfo(name="imgUrl")
    val imgUrl: String
)

fun List<ChapterEntity>.asDomainModel():List<Chapter>{
    return map{
        Chapter(
            id = it.id,
            title = it.title,
            imgUrl=it.imgUrl
        )
    }
}

fun ArrayList<ChapterFb>.asDatabaseModel(): Array<ChapterEntity> {
    return map {
        ChapterEntity (
            id = it.id,
            title = it.title,
            imgUrl = it.imgUrl)
    }.toTypedArray()
}

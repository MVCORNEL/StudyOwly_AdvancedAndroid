package com.vcmanea.studyowly.database.chapter

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ChapterDao{
    //when we query room and return a list it will block the UI thread
    //when we use a LiveData, Room will to the database query in the background
    //and will update the LiveData every time new data is written to the table
    @Query("SELECT * FROM ChapterEntity ORDER BY id")
    fun getAll(): LiveData<List<ChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg chapters: ChapterEntity)

    @Query("UPDATE ChapterEntity SET isComplete=1 WHERE id= :chapterID")
    fun updateChapter(chapterID:Long)
}
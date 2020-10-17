package com.vcmanea.studyowly.database.lesson.theory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TheoryDao{
    //when we query room and return a list it will block the UI thread
    //when we use a LiveData, Room will to the database query in the background
    //and will update the LiveData every time new data is written to the table
    @Query("SELECT * FROM theoryentity")
    fun getAll(): LiveData<List<TheoryEntity>>



    @Query("SELECT * FROM theoryentity WHERE chapter_id = (:chapterId)")
    suspend fun loadQuizByID(chapterId:Long): List<TheoryEntity>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg theories: TheoryEntity)

}
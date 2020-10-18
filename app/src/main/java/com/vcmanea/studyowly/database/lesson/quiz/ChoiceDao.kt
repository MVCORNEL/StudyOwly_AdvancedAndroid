package com.vcmanea.studyowly.database.lesson.quiz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChoiceDao{
    //when we query room and return a list it will block the UI thread
    //when we use a LiveData, Room will to the database query in the background
    //and will update the LiveData every time new data is written to the table
    @Query("SELECT * FROM ChoiceEntity")
    fun getAll(): LiveData<List<ChoiceEntity>>


    @Query("SELECT * FROM ChoiceEntity WHERE quiz_id = (:quizId)")
    suspend fun loadChoiceListByID(quizId:Long):List<ChoiceEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg choices: ChoiceEntity)

}
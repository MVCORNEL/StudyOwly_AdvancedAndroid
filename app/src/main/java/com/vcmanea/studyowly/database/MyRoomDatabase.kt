package com.vcmanea.studyowly.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.vcmanea.studyowly.database.chapter.ChapterDao
import com.vcmanea.studyowly.database.chapter.ChapterEntity
import com.vcmanea.studyowly.database.lesson.quiz.ChoiceDao
import com.vcmanea.studyowly.database.lesson.quiz.ChoiceEntity
import com.vcmanea.studyowly.database.lesson.quiz.QuizDao
import com.vcmanea.studyowly.database.lesson.quiz.QuizEntity
import com.vcmanea.studyowly.database.lesson.theory.TheoryDao
import com.vcmanea.studyowly.database.lesson.theory.TheoryEntity
import com.vcmanea.studyowly.database.lesson.theory.TheoryPartDao
import com.vcmanea.studyowly.database.lesson.theory.TheoryPartEntity

@Database(entities = [ChapterEntity::class, TheoryEntity::class, TheoryPartEntity::class, QuizEntity::class, ChoiceEntity::class], version = 1,exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract val chapterDao: ChapterDao
    abstract val theoryDao: TheoryDao
    abstract val theoryPartDao: TheoryPartDao
    abstract val quizDao: QuizDao
    abstract val choiceDao: ChoiceDao


    companion object {
        //VOLATILE
        //Annotated with volatile makes sure that the value of Instance is always up to data, and the same to all execution threats
        //A value of a volatile variable will never be cached, and all write sand reads will be done to and from the main memory
        //Means by changes made from a thread INSTANCE will be visible to other threads immediately
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase {
            //Multiple threads can potentially ask for a database instance at the same time
            //leaving us with to instances instead of one
            //-> only one thread of execution can enter this block of code at a time, and makes sure the database only get initialised once
            synchronized(this) {
                var instance = INSTANCE
                //SMART CAST is only available to local variables not to member variables
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "kotlin_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    //MIGRATION -> not covered with our database(Destructive migration)
                    //Normally we would have to provide a migration object with a migration strategy when we create the database.
                    //Migration means when we change the database schema, by changing the number or the type of columns
                    //we need a way to convert the existing tables and data into the new schema
                    //A migration object is an object which defines how you take all your rows with your old schema and convert them to rows in the new schema.
                    //If a user updates from a version with a old database schema into a newer version with a new database schema, their sleepData is not lost
                    //In our case we just wipe and rebuild the database instead of migrating
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}
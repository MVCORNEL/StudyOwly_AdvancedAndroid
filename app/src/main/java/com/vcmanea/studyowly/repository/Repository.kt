package com.vcmanea.studyowly.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vcmanea.studyowly.application.MyApplication
import com.vcmanea.studyowly.database.MyRoomDatabase
import com.vcmanea.studyowly.database.chapter.asDatabaseModel
import com.vcmanea.studyowly.database.chapter.asDomainModel
import com.vcmanea.studyowly.database.lesson.quiz.asDatabaseModel
import com.vcmanea.studyowly.database.lesson.quiz.asDomainModel
import com.vcmanea.studyowly.database.lesson.quiz.asQuizDomainModel
import com.vcmanea.studyowly.database.lesson.theory.asDatabaseModel
import com.vcmanea.studyowly.database.lesson.theory.asDomainModel
import com.vcmanea.studyowly.domain.chapter.Chapter
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.theory.Theory
import com.vcmanea.studyowly.domain.tutorial.theory.TheoryPart
import com.vcmanea.studyowly.firebase.FirebaseDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor() : FirebaseDB.OnDownloadComplete {

    val firebaseDB = FirebaseDB.getInstance(this)
    val database: MyRoomDatabase = MyRoomDatabase.getDatabase(MyApplication.context)

    //CHAPTERS -> LIVE DATA
    val chaptersLD: LiveData<List<Chapter>> = Transformations.map(database.chapterDao.getAll()) {
        it.asDomainModel()
    }

    //TUTORIAL -> MUTABLE LIVE DATA
    val tutorial= MutableLiveData<Tutorial?>()
    //PROGRESS BAR
    var progressMax = Transformations.distinctUntilChanged(Transformations.map(tutorial) {
        it?.lessonsListSize
    })
    var currentProgress = Transformations.distinctUntilChanged(Transformations.map(tutorial) {
        it?.progressBarIndex
    })
    //PAGER
    //Lesson List -> Pager
    var lessonsList = Transformations.distinctUntilChanged(Transformations.map(tutorial) {
        it?.lessonList
    })

     fun completeChapter(chapterID:Long){
        database.chapterDao.updateChapter(chapterID)
    }

    //TUTORIAL
    suspend fun setTutorial(chapterID: Long){
    //CREATE A TUTORIAL
        val tutorial = Tutorial()
        //THEORY LIST -> based on chapter id
        val theoryList: List<Theory> = database.theoryDao.loadQuizByID(chapterID).asDomainModel()
        for (theory in theoryList) {
            Timber.d("Theory")
            //ADD THE PART TO THE THEORY
            val theoryID = theory.id
            val theoryParts: List<TheoryPart> = database.theoryPartDao.loadTheoryListByID(theoryID).asDomainModel()
            theory.addPartList(theoryParts)
        }

        //Quiz LIST -> based on chapter id
        //VEry important is identify which quizzes are single choice and which are double because the quiz model class is abstract and cannot be initialized
        val quizList: List<Quiz> = database.quizDao.loadQuizByID(chapterID).asQuizDomainModel()

        for (quiz in quizList) {
            Timber.d( "Quiz")
            val quizID = quiz.id
            val quizChoice: List<Quiz.Choice> = database.choiceDao.loadChoiceListByID(quizID).asDomainModel()

            quiz.addChoices(quizChoice)

        }
        Timber.d(" parts size ${theoryList.get(1).theoryParts.size} ")
        tutorial.addOrderLessonsAndListener(theoryList)
        tutorial.addOrderLessonsAndListener(quizList)

        this.tutorial.postValue(tutorial)
    }

    //*****************************************************REFRESH COURSE DATA************************************************//
    fun refreshKotlinFirebase() {
        firebaseDB.downloadData()
    }

    override fun onDownloadComplete() {
        //ADD EVERYTHING TO DATABASE WITHIN THE CALLBACK
        Timber.d("onDownloadComplete data completely downloaded")
        val chapters = firebaseDB.chapterList
        val theory = firebaseDB.theoryList
        val theoryPart = firebaseDB.theoryPartList
        val quiz = firebaseDB.quizList
        val choice = firebaseDB.quizChoicesFirebase

        CoroutineScope(Dispatchers.IO).launch {
            database.chapterDao.insertAll(*chapters.asDatabaseModel())
            database.theoryDao.insertAll(*theory.asDatabaseModel())
            database.theoryPartDao.insertAll(*theoryPart.asDatabaseModel())
            database.quizDao.insertAll(*quiz.asDatabaseModel())
            database.choiceDao.insertAll(*choice.asDatabaseModel())
        }
    }

}
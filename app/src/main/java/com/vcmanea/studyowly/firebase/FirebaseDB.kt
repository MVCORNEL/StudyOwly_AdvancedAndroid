package com.vcmanea.studyowly.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vcmanea.studyowly.firebase.dto.*
import timber.log.Timber

const val CHAPTERS = "Chapters"
const val CHAPTER_TITLE = "title"
const val CHAPTER_IMG_URL = "imgUrl"
const val CHAPTER_LESSON = "lesson"

const val LESSON_TYPE = "type"
const val LESSON_TYPE_QUIZ = "quiz"
const val LESSON_TYPE_THEORY = "theory"

const val LESSON_QUIZ_CHOICES = "choices"

const val LESSON_QUIZ_QUESTION = "question"
const val QUIZ_MULTIPLE_OR_SINGLE = "multiple_choice"
const val QUIZ_CHOICE_CHOICE = "choice"
const val QUIZ_CHOICE_IS_CORRECT = "isCorrect"

const val LESSON_THEORY_PARTS = "parts"
const val THEORY_PART_TYPE = "type"
const val THEORY_PART_CONTENT = "content"

class FirebaseDB private constructor(val listener: OnDownloadComplete) {

    interface OnDownloadComplete {
        fun onDownloadComplete()
    }

    //RealtimeDatabase
    private val mDatabase = FirebaseDatabase.getInstance()
    private val mDatabaseRef = mDatabase.reference

    //Chapter
    val chapterList = ArrayList<ChapterFb>()

    //THEORY
    val theoryList = ArrayList<TheoryFb>()
    val theoryPartList = ArrayList<TheoryPartFb>()

    //QUIZ
    val quizList = ArrayList<QuizFb>()
    val quizChoicesFirebase = ArrayList<QuizChoicesFb>()


    companion object {
        private var INSTANCE: FirebaseDB? = null

        fun getInstance(listener: OnDownloadComplete): FirebaseDB {
            if (INSTANCE == null) {
                INSTANCE = FirebaseDB(listener)
            }
            return INSTANCE as FirebaseDB
        }
    }

    fun downloadData() {
        //clear all other list
        chapterList.clear()

        val query = mDatabaseRef.child(CHAPTERS)
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Timber.w(  "Chapters download canceled. $error")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    //CHAPTER
                    val chaptersSnap = snapshot.value as ArrayList<*>
                    var chapterID: Long = 1
                    for (elementChapter in chaptersSnap) {

                        //ADD CHAPTER
                        val chapterJSON = elementChapter as HashMap<*, *>
                        val chapterFb = ChapterFb(chapterJSON[CHAPTER_TITLE] as String, chapterJSON[CHAPTER_IMG_URL] as String, chapterID)
                        chapterList.add(chapterFb)
                        Timber.d(" id : ${chapterFb.id}  -> ${chapterFb.title}")

                        //LESSON -> interface of QUIZ AND THEORY
                        val lessonSnap = chapterJSON[CHAPTER_LESSON] as ArrayList<*>
                        var lessonIndex: Long = 1

                        for (elementLesson in lessonSnap) {
                            // -> generate indexes based on chapter index -> each tutorial will have a range of 100 indexes
                            val lessonID = (chapterID * 100) + lessonIndex
                            val lessonJSON = elementLesson as HashMap<*, *>
                            //ADD LESSON(QUIZ AND THEORY)
                            when (lessonJSON[LESSON_TYPE]) {
                                //THEORY
                                LESSON_TYPE_THEORY -> {
                                    //ADD THEORY
                                    val theoryFB = TheoryFb(lessonID, chapterID)
                                    theoryList.add(theoryFB)
                                    Timber.d( "Theory - >id = ${theoryFB.id} , chapter_id = ${theoryFB.chapter_id}")

                                    //THEORY PARTS
                                    val theoryPartSnap = lessonJSON[LESSON_THEORY_PARTS] as List<*>
                                    var theoryPartIndex: Long = 1
                                    for (elementPart in theoryPartSnap) {
                                        //ID -> each id is base on the previous id in this way it won't occur twice
                                        val theoryPartID: Long = (lessonID * 100) + theoryPartIndex
                                        //ADD THEORY PARTS
                                        val theoryPartJSON = elementPart as HashMap<*, *>
                                        val theoryPartFb = TheoryPartFb(theoryPartID, lessonID, theoryPartJSON[THEORY_PART_TYPE] as String, theoryPartJSON[THEORY_PART_CONTENT] as String)

                                        theoryPartList.add(theoryPartFb)
                                        Timber.d("Theory Part -> id : ${theoryPartFb.id} tutorial id: ${lessonID}-> ${theoryPartFb.type} ${theoryPartFb.content}")
                                        //INCREMENT THEORY PART INDEX
                                        theoryPartIndex++
                                    }
                                }

                                //QUIZ
                                LESSON_TYPE_QUIZ -> {
                                    //ADD THE QUIZ
                                    val quizFB = QuizFb(lessonID, chapterID, lessonJSON[LESSON_QUIZ_QUESTION] as String, lessonJSON[QUIZ_MULTIPLE_OR_SINGLE] as Boolean)
                                    quizList.add(quizFB)
                                    Timber.d( " Quiz - >id=${lessonID} , chapter_id = ${chapterID}, ${quizFB.question}, ${quizFB.multiple_choice}")
                                    //ADD CHOICES
                                    val quizChoicesSnap = lessonJSON[LESSON_QUIZ_CHOICES] as List<*>
                                    var quizChoiceIndex: Long = 1

                                    for (choice in quizChoicesSnap) {

                                        val quizChoiceID: Long = (lessonID * 100) + quizChoiceIndex
                                        val quizChoiceJSON = choice as HashMap<*, *>
                                        val choiceFb = QuizChoicesFb(quizChoiceID, lessonID, quizChoiceJSON[QUIZ_CHOICE_CHOICE] as String, quizChoiceJSON[QUIZ_CHOICE_IS_CORRECT] as Boolean)
                                        quizChoicesFirebase.add(choiceFb)
                                        Timber.d( "Choice: id : ${quizChoiceID} quiz_id:${lessonID}, ${choiceFb.choice}, ${choiceFb.isCorrect}")
                                        //INCREMENT CHOICE INDEX
                                        quizChoiceIndex++
                                    }
                                }

                                //ERROR
                                else -> {
                                    throw Exception("Unknown type parameter")
                                }
                            }
                            //INCREMENT CHAPTER INDEX
                            lessonIndex++
                        }
                        //INCREMENT CHAPTER INDEX(ID)
                        chapterID++
                    }
                    listener.onDownloadComplete()

                } catch (exp: Exception) {
                    Timber.e("Failed to theory snapshot. ${exp}")
                }
            }
        })
    }


}
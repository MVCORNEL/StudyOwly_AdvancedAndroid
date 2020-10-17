
package com.vcmanea.studyowly.domain.tutorial
import java.util.*
import kotlin.collections.ArrayList

const val TUTORIAL_TYPE_THEORY = "Theory"
const val TUTORIAL_TYPE_SINGLE_CHOICE = "Single-choice"
const val TUTORIAL_TYPE_MULTIPLE_CHOICE = "Multiple-choice"

class Tutorial : Lesson.OnLessonCompleted {

    var lessonList: ArrayList<Lesson> = ArrayList()
    var unlockedElements = 1

    //For PROGRESS BAR
    var lessonsListSize = 0
    var progressBarIndex = 0

    var isCompleted = false

    fun getLessonByIndex(index: Int): Lesson {
        return lessonList.get(index)
    }

    /**
    @param [List<Lesson>]
     */
    fun addOrderLessonsAndListener(newList: List<Lesson>) {
        for (elements in newList) {
            elements.setOnCompleteListener(this)
        }
        this.lessonList.addAll(newList)
        Collections.sort(lessonList)
        lessonsListSize = lessonList.size
    }

    override fun onLessonCompleted() {
        updateUnlockedElements()
    }

    //UNLOCK ELEMENTS BASE ON COMPLETED LESSONS
    fun updateUnlockedElements() {
        var unlockedElementsCount=1
        //first element will always be unlocked -> the other ones will get determined based on completed lessons
        for(lesson in lessonList){
            if(lesson.isCompleted){
                unlockedElementsCount++
            }
        }
        // When the last element is completed the unlock elements count will be higher than the list size, in that case will return just the list size
        // Also the tutorial will become completed
        if(unlockedElementsCount>lessonsListSize){
            unlockedElements=lessonsListSize
            isCompleted=true
            return
        }
        unlockedElements=unlockedElementsCount
    }
}
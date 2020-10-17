package com.vcmanea.studowly

import com.vcmanea.studyowly.domain.tutorial.Lesson
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.domain.tutorial.quiz.MultipleChoiceQuiz
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.SingleChoiceQuiz
import com.vcmanea.studyowly.domain.tutorial.theory.Theory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.collections.ArrayList

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestTutorial {

    var quiz1: Quiz? = null
    var quiz2: Quiz? = null
    var theory: Theory? = null
    var theory2: Theory? = null
    var lessonsList: ArrayList<Lesson> ?= null
    var tutorial: Tutorial? = null

    @BeforeEach
    fun initialization() {
        quiz1 = SingleChoiceQuiz("First choice", 1)
        quiz2 = MultipleChoiceQuiz("Second choice", 3)
        theory = Theory(0)
        theory2 = Theory(2)
        lessonsList= ArrayList()
        lessonsList?.add(quiz1 as SingleChoiceQuiz)
        lessonsList?.add(quiz2 as MultipleChoiceQuiz)
        lessonsList?.add(theory as Theory)
        lessonsList?.add(theory2 as Theory)
        tutorial=Tutorial()
        tutorial?.addOrderLessonsAndListener(lessonsList!!)

    }

    @AfterEach
    fun cleanup() {
        quiz1 = null
        quiz2 = null
        theory = null
        theory2 = null
        lessonsList?.clear()
        tutorial = null
    }


    @Test
    fun addAndSortLessons() {
        var id=0L
        for (lesson in tutorial!!.lessonList){
            assertEquals(id,lesson.id)
            id++
        }
    }

    @Test
    fun getLessonSize(){
        assertEquals(4,tutorial?.lessonsListSize)
    }

    @Test
    fun updateUnlockedElements(){
        assertEquals(false,tutorial?.isCompleted)
        assertEquals(1,tutorial?.unlockedElements)

        tutorial?.lessonList?.get(0)?.completeLesson()
        assertEquals(2,tutorial?.unlockedElements)
        assertEquals(false,tutorial?.isCompleted)

        tutorial?.lessonList?.get(1)?.completeLesson()
        assertEquals(3,tutorial?.unlockedElements)
        assertEquals(false,tutorial?.isCompleted)

        tutorial?.lessonList?.get(2)?.completeLesson()
        assertEquals(4,tutorial?.unlockedElements)
        assertEquals(false,tutorial?.isCompleted)

        tutorial?.lessonList?.get(3)?.completeLesson()
        assertEquals(4,tutorial?.unlockedElements)
        assertEquals(true,tutorial?.isCompleted)
    }
}
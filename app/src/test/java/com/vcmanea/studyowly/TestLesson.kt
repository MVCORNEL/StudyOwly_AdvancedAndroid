package com.vcmanea.studyowly

import com.vcmanea.studyowly.domain.tutorial.Lesson
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.domain.tutorial.quiz.MultipleChoiceQuiz
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.SingleChoiceQuiz
import com.vcmanea.studyowly.domain.tutorial.theory.Theory

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*
import kotlin.collections.ArrayList


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestLesson {

    var quiz1: Quiz? = null
    var quiz2: Quiz? = null
    var theory: Theory? = null
    var theory2: Theory? = null
    var lessonsList: ArrayList<Lesson>? = null
    var tutorial:Tutorial?=null

    @BeforeEach
    fun initialization() {
        quiz1 = SingleChoiceQuiz("First choice", 0)
        quiz2 = MultipleChoiceQuiz("Second choice", 2)
        theory = Theory(3)
        theory2 = Theory(1)
        lessonsList = ArrayList<Lesson>()
        lessonsList?.add(quiz1 as SingleChoiceQuiz)
        lessonsList?.add(quiz2 as MultipleChoiceQuiz)
        lessonsList?.add(theory as Theory)
        lessonsList?.add(theory2 as Theory)
        tutorial= Tutorial()
        tutorial?.addOrderLessonsAndListener(lessonsList as ArrayList<Lesson>)

    }

    @AfterEach
    fun cleanup() {
        quiz1 = null
        quiz2 = null
        theory = null
        theory2 = null
        lessonsList = null
        tutorial =null
    }


    @Test
    fun `sort lessons by id`() {
        Collections.sort(lessonsList)
        assertEquals(lessonsList?.get(0)?.id, 0)
        assertEquals(lessonsList?.get(1)?.id, 1)
        assertEquals(lessonsList?.get(2)?.id, 2)
        assertEquals(lessonsList?.get(3)?.id, 3)
    }


    @Test
    fun ` lesson is complete first time`() {
        assertEquals(false, lessonsList?.get(1)?.isCompleted)
        lessonsList?.get(1)?.completeLesson()
        assertEquals(true, lessonsList?.get(1)?.isCompleted)
        lessonsList?.get(2)?.completeLesson()
        assertEquals(true, lessonsList?.get(2)?.isCompleted)
    }

}
package com.vcmanea.studowly

import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestChoice {

    var choice: Quiz.Choice? = null
    var choice2: Quiz.Choice? = null
    var choice3: Quiz.Choice? = null
    var choice4: Quiz.Choice? = null

    @BeforeEach
    fun initialization() {
        choice = Quiz.Choice("First choice", false)
        choice2 = Quiz.Choice("Second choice", false)
        choice3 = Quiz.Choice("Third choice", true )
        choice4 = Quiz.Choice("Fourth choice", true)
    }

    @AfterEach
    fun cleanup() {
        choice = null
        choice2 = null
        choice3 = null
        choice4 = null
    }

    @Test
    fun isCorrect() {
        assertEquals(true, choice?.isCorrectSelected())
        assertEquals(true, choice2?.isCorrectSelected())
        assertNotEquals(true, choice3?.isCorrectSelected())
        assertNotEquals(true, choice4?.isCorrectSelected())
    }

    @Test
    fun isCorrectSelection() {
        choice3?.isSelected=true
        assertEquals(true, choice3?.isCorrectSelected())
        choice3?.isSelected=false
        assertEquals(false, choice3?.isCorrectSelected())
    }
}
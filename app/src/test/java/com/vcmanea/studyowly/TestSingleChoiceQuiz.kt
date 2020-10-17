package com.vcmanea.studowly

import com.vcmanea.studyowly.domain.tutorial.quiz.CHOICE_LETTER
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.QuizState
import com.vcmanea.studyowly.domain.tutorial.quiz.SingleChoiceQuiz
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestSingleChoiceQuiz {

    var choice: Quiz.Choice? = null
    var choice2: Quiz.Choice? = null
    var choice3: Quiz.Choice? = null
    var choice4: Quiz.Choice? = null
    var quiz: Quiz? = null

    @BeforeEach
    fun initialization() {

        choice = Quiz.Choice("First choice", false)
        choice2 = Quiz.Choice("Second choice", false)
        choice3 = Quiz.Choice("Third choice", false)
        choice4 = Quiz.Choice("Fourth choice", true)

        val choiceList = ArrayList<Quiz.Choice>()
        choiceList.add(choice as Quiz.Choice)
        choiceList.add(choice2 as Quiz.Choice)
        choiceList.add(choice3 as Quiz.Choice)
        choiceList.add(choice4 as Quiz.Choice)
        quiz = SingleChoiceQuiz("This is the question", 1, 1)
        quiz?.addChoices(choiceList)


    }

    @AfterEach
    fun cleanup() {
        choice = null
        choice2 = null
        choice3 = null
        choice4 = null
        quiz = null
    }

    @Test
    fun `SingleChoiceQuiz selectChoice`() {
        //ALL CHOICES ARE DESELECTED
        assertEquals(QuizState.NO_CHOICE_SELECTED,quiz?.state)
        //SELECT FIRST CHOICE -> QUIZ BECOMES SELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        assertEquals(QuizState.CHOICE_SELECTED,quiz?.state)
        //DESELECT FIRST CHOICE -> QUIZ BECOMES DESELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        assertEquals(QuizState.NO_CHOICE_SELECTED,quiz?.state)

        //SELECT FIRST(A) CHOICE. A->T, B->F, C->F, D-F
        quiz?.selectChoice(CHOICE_LETTER.A)
        assertEquals(quiz?.choicesMap?.get(CHOICE_LETTER.A)?.isSelected,true)
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.B)?.isSelected,true)
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.C)?.isSelected,true)
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.D)?.isSelected,true)

        quiz?.selectChoice(CHOICE_LETTER.B)
        //SELECT SECOND(B) CHOICE. A->F, B->T, C->F, D-F
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.A)?.isSelected,true)
        assertEquals(quiz?.choicesMap?.get(CHOICE_LETTER.B)?.isSelected,true)
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.C)?.isSelected,true)
        assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.D)?.isSelected,true)
    }
}
package com.vcmanea.studowly

import com.vcmanea.studyowly.domain.tutorial.quiz.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestQuiz {

    var choice: Quiz.Choice? = null
    var choice2: Quiz.Choice? = null
    var choice3: Quiz.Choice? = null
    var choice4: Quiz.Choice? = null
    var quiz: Quiz? = null

    @BeforeEach
    fun initialization() {

        choice = Quiz.Choice("First choice", true)
        choice2 = Quiz.Choice("Second choice", false)
        choice3 = Quiz.Choice("Third choice", false)
        choice4 = Quiz.Choice("Fourth choice", false)

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
    fun `addChoices ChoicesRangeException`() {
        assertThrows<ChoicesRangeException> {
            //INITIALIZE TUTORIAL
            val c1 = Quiz.Choice("First choice", false)
            val c2 = Quiz.Choice("Second choice", false)
            val c3 = Quiz.Choice("Third choice", false)
            val c4 = Quiz.Choice("Fourth choice", true)
            val c5 = Quiz.Choice("Fourth choice", true)

            val cList = ArrayList<Quiz.Choice>()
            cList.add(c1)
            cList.add(c2)
            cList.add(c3)
            cList.add(c4)
            cList.add(c5)

            quiz = SingleChoiceQuiz("This is the question", 1, 1)
            quiz?.addChoices(cList)

        }
    }

    @Test
    fun checkAnswers() {
        //Check if the quiz is correct when nothing is selected
        assertNotEquals( QuizState.CORRECT_ANSWERED, quiz?.state)

        //SELECT THE CORRECT CHOICE -> state CHOICE_SELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        //CHECK THE QUIZ -> state CORRECT ANSWERED
        quiz?.checkAnswers()
        assertEquals( QuizState.CORRECT_ANSWERED, quiz?.state)


        //SELECT THE WRONG CHOICE -> state CHOICE_SELECTED
        quiz?.selectChoice(CHOICE_LETTER.B)
        //CHECK THE QUIZ -> state CORRECT ANSWERED
        quiz?.checkAnswers()
        assertEquals( QuizState.WRONG_ANSWERED, quiz?.state)
    }

    @Test
    fun tryAgain() {
        //SELECT THE CORRECT CHOICE -> state CHOICE_SELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        assertEquals( QuizState.CHOICE_SELECTED, quiz?.state)
        //TRY AGAIN QUIZ -> state NO_CHOICE_SELECTED
        quiz?.tryAgain()
        assertEquals( QuizState.NO_CHOICE_SELECTED, quiz?.state)

    }

}
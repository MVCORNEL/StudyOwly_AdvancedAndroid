package com.vcmanea.studowly

import com.vcmanea.studyowly.domain.tutorial.quiz.*
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMultipleChoiceQuiz {

    var choice: Quiz.Choice? = null
    var choice2: Quiz.Choice? = null
    var choice3: Quiz.Choice? = null
    var choice4: Quiz.Choice? = null
    var quiz: Quiz? = null

    @BeforeEach
    fun initialization() {


        choice = Quiz.Choice("First choice", false)
        choice2 = Quiz.Choice("Second choice", false)
        choice3 = Quiz.Choice("Third choice", true)
        choice4 = Quiz.Choice("Fourth choice", true)

        val choiceList = ArrayList<Quiz.Choice>()
        choiceList.add(choice as Quiz.Choice)
        choiceList.add(choice2 as Quiz.Choice)
        choiceList.add(choice3 as Quiz.Choice)
        choiceList.add(choice4 as Quiz.Choice)
        quiz = MultipleChoiceQuiz("This is the question", 1, 1)
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
    fun `MultipleChoiceQuiz selectChoice`() {
        //ALL CHOICES ARE DESELECTED
        Assertions.assertEquals(QuizState.NO_CHOICE_SELECTED,quiz?.state)
        //SELECT FIRST CHOICE -> QUIZ BECOMES SELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        Assertions.assertEquals(QuizState.CHOICE_SELECTED,quiz?.state)
        //DESELECT FIRST CHOICE -> QUIZ BECOMES DESELECTED
        quiz?.selectChoice(CHOICE_LETTER.A)
        Assertions.assertEquals(QuizState.NO_CHOICE_SELECTED,quiz?.state)

        //SELECT FIRST(A) CHOICE. A->T, B->F, C->F, D-F
        quiz?.selectChoice(CHOICE_LETTER.A)
        Assertions.assertEquals(quiz?.choicesMap?.get(CHOICE_LETTER.A)?.isSelected,true)
        Assertions.assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.B)?.isSelected,true)
        Assertions.assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.C)?.isSelected,true)
        Assertions.assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.D)?.isSelected,true)

        quiz?.selectChoice(CHOICE_LETTER.B)
        //SELECT SECOND(B) CHOICE. A->T, B->T, C->F, D-F
        Assertions.assertEquals(quiz?.choicesMap?.get(CHOICE_LETTER.A)?.isSelected,true)
        Assertions.assertEquals(quiz?.choicesMap?.get(CHOICE_LETTER.B)?.isSelected,true)
        Assertions.assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.C)?.isSelected,true)
        Assertions.assertNotEquals(quiz?.choicesMap?.get(CHOICE_LETTER.D)?.isSelected,true)
    }


}
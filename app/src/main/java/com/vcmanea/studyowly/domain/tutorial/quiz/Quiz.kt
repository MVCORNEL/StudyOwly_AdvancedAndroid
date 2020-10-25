

package com.vcmanea.studyowly.domain.tutorial.quiz

import com.vcmanea.studyowly.domain.tutorial.Lesson

object ChoiceLetter {
    const val A = "A"
    const val B = "B"
    const val C = "C"
    const val D = "D"
    val ALLOWED_RANGE = "A".."D"
}

/**
NO_CHOICE_SELECTED -> no choice selected, btn is disabled
CHOICE_SELECTED -> at least one choice is selected, the button has the text CHECK and is active
WRONG_ANSWERED -> the quiz has been wrongly answered, the choice are disabled and the button turn into try again, activating and reset the quiz to the IDLE state
CORRECT_ANSWERED -> the quiz is correctly answered, and the choice become disabled, the button turn in next
 */
enum class QuizState {
    NO_CHOICE_SELECTED, CHOICE_SELECTED, WRONG_ANSWERED, CORRECT_ANSWERED
}

abstract class Quiz(var question:String,id: Long, chapter_id: Long) : Lesson(id, chapter_id) {

    var state = QuizState.NO_CHOICE_SELECTED
    val choicesMap: HashMap<String, Choice> = HashMap()

    abstract fun selectChoice(key: String)


    /**
     * Checks the quiz has at least one question selected at a particular point of time
     * */
    fun checkForSelection() {
        choicesMap.forEach { choice ->
            if (choice.value.isSelected) {
                state = QuizState.CHOICE_SELECTED
                return@checkForSelection
            }
        }
        state = QuizState.NO_CHOICE_SELECTED
    }

    /**
     * Deselects all selected choices and set the quiz state to QuizState.NO_CHOICE_SELECTED
     */
    fun tryAgain() {
        choicesMap.forEach {
            it.value.isSelected = false
        }
        state = QuizState.NO_CHOICE_SELECTED
    }


    /**
     * Checks the quiz if it is correct answered, based on [choicesMap] object method isCorrectAnswered()hen
     * quiz state gets WRONG_ANSWERED, if the quiz was wrongly answered
     * quiz state gets CORRECT_ANSWERED, if the quiz is correctly answered
     */
    fun checkAnswers(){
        choicesMap.forEach { choice ->
            if (!choice.value.isCorrectSelected()) {
                state = QuizState.WRONG_ANSWERED
                return@checkAnswers
            }
        }
        isCompleted=true
        state = QuizState.CORRECT_ANSWERED
    }


     fun addChoices(choices: List<Choice>) {
        for (choice in choices) {
            when (choicesMap.size) {
                0 -> choicesMap[ChoiceLetter.A] = choice
                1 -> choicesMap[ChoiceLetter.B] = choice
                2 -> choicesMap[ChoiceLetter.C] = choice
                3 -> choicesMap[ChoiceLetter.D] = choice
                else -> {
                   throw ChoicesRangeException("A quiz should have at the most 4 CHOICES")
                }
            }
        }
    }


    class Choice(val choice: String, val isCorrect: Boolean, val id: Long = 0, val quiz_id: Long = 0) {
        var isSelected: Boolean = false
        /**
         * @return [Boolean] true if and only if the choice is selected and is correct, and false otherwise
         */
        fun isCorrectSelected():Boolean{
            return isCorrect == isSelected
        }

        /**
         * @return [Boolean] true if the choice is isCorrect state and isSelected state are the same, false otherwise
         */
        fun isCorrectAndSelected(): Boolean {
            if (isSelected && isCorrect) {
                return true
            }
            return false
        }
    }

}



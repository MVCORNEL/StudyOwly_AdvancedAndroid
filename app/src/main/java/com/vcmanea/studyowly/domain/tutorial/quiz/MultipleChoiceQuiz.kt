package com.vcmanea.studyowly.domain.tutorial.quiz


/**
 * Quiz class that  allows multiple choices to be checked(selected) at the same time
 */
class MultipleChoiceQuiz(question: String,id:Long=0, chapter_id: Long = 0) :
    Quiz(question,id,chapter_id) {

    /**
     * Whenever a choice is pressed(checked) if the current state is selected it will become unselected,
     * if it is unselected will become selected also verify if there is any active at a point of time
     * @param key of [String] whose attribute value is in the range of [ChoiceLetter.ALLOWED_RANGE]
     */
    override fun selectChoice(key: String) {
        if (key !in ChoiceLetter.ALLOWED_RANGE)
            throw Exception("Choice character required is A,B,C,D")

        val currentChoice = super.choicesMap[key]
        currentChoice?.let {
            currentChoice.isSelected = !currentChoice.isSelected
            checkForSelection()

        }
    }


}
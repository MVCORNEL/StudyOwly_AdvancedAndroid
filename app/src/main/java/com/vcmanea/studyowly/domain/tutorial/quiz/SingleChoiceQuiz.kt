package com.vcmanea.studyowly.domain.tutorial.quiz

/**
 * Quiz class which allows that only one choice to be checked at a point of time
 */
class SingleChoiceQuiz(question: String, id: Long=0, chapter_id: Long=0) :
    Quiz(question, id, chapter_id) {

    /**
     * Whenever a choice is pressed when it is selected will become unselected
     * Whenever a choice is unselected, before it becomes selected all the other choices which are selected need to become unselected
     * because only one choice is allowed to be selected at a point of time + also verify if there is any active at a point of time
     * @param key of [String] whose attribute value is in the range of [ChoiceLetter.ALLOWED_RANGE]
     */
    override fun selectChoice(key: String) {
        if (key !in ChoiceLetter.ALLOWED_RANGE)
            throw Exception("Choice character required is [A,B,C,D]")
        val currentChoice = super.choicesMap[key]

        currentChoice?.let {
            if (currentChoice.isSelected) {
                currentChoice.isSelected = false
            }
            else {
                //set all choices which are selected to unselected
                super.choicesMap.forEach {
                    val tempChoice = it.value
                    if (tempChoice.isSelected) {
                        tempChoice.isSelected = false
                    }
                }
                currentChoice.isSelected = true
            }
            checkForSelection()
        }
    }
}
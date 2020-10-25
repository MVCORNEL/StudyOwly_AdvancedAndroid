package com.vcmanea.studyowly.ui.quiz

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.vcmanea.studyowly.R
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.QuizState


//CHOICE
/**
 * Method used to bind the checkable adapter
 * @param checkedTextView which must contain a tag is in the range of [A,B,C,D], the Tag will be the key used to retrieve the choice(value) within choices map
 * @param choices HashMap used to bind data to the checkedTextView
 */
@BindingAdapter("setChoice", "disableEnableChoice")
fun bindCheckedText(checkedTextView: CheckedTextView, choices: HashMap<String, Quiz.Choice>?, quizState: QuizState?) {
    //Set the choice text and its selectable value
    choices?.let {
        val currentChoice = choices[checkedTextView.tag as String]
        Log.d("TAG", "${checkedTextView.tag}")
        currentChoice?.let {
            checkedTextView.text = currentChoice.choice
            checkedTextView.isChecked = currentChoice.isSelected
        }
    }

    //disable choices when needed + put the check on the correct answers
    quizState.let {
        if (it == QuizState.CORRECT_ANSWERED || it == QuizState.WRONG_ANSWERED) {
            checkedTextView.isClickable = false
            checkedTextView.isFocusable = false
            checkedTextView.isClickable = false
        }
        else {
            checkedTextView.isClickable = true
            checkedTextView.isFocusable = true
            checkedTextView.isClickable = true
        }
    }
    //tick it if it is correct and selected
    if(quizState==QuizState.CORRECT_ANSWERED){
        choices?.let {
            val currentChoice = choices[checkedTextView.tag as String]

            if(currentChoice!= null && currentChoice.isCorrectAndSelected()){
                checkedTextView.setCheckMarkDrawable(R.drawable.ic_baseline_check_24)
            }
        }
    }
}


//CHECK
/**
 * Method used enable the button in the CHECKABLE,TRY AGAIN and COMPLETED state and to disable it in IDLE(when no choice is selected)
 * Also this adapter set the text of the button depends of it is particular state + and also the animation of the button when the quiz changes states
 * @param button
 * @param state the current state of the QUIZ
 */
@BindingAdapter("bindCheckBtnState")
fun bindCheckButton(button: Button, state: QuizState?) {
    state?.let {
        if (it == QuizState.NO_CHOICE_SELECTED) {
            button.isClickable = false
            button.isFocusable = false
            button.alpha = 0.3f
        }
        else {
            button.isClickable = true
            button.isFocusable = true
            button.alpha = 1f
        }
    }

    if (state == QuizState.WRONG_ANSWERED) {
        val animator = AnimatorInflater.loadAnimator(button.context, R.animator.btn_set_anim) as AnimatorSet
        animator.setTarget(button)
        animator.start()
        button.text = button.context.getString(R.string.try_again)
    }
    if (state == QuizState.CORRECT_ANSWERED) {
        val animator = AnimatorInflater.loadAnimator(button.context, R.animator.btn_set_anim) as AnimatorSet
        animator.setTarget(button)
        animator.start()
        button.text = button.context.getString(R.string.next)

    }
    if (state == QuizState.CHOICE_SELECTED) {
        button.text = button.context.getString(R.string.check)
    }


}
//SKIP
@BindingAdapter("bindSkipBtnState")
fun bindSkipButton(button: Button, state: QuizState?) {
    state?.let {
        if (it == QuizState.CORRECT_ANSWERED) {
            button.isClickable = false
            button.isFocusable = false
            button.alpha = 0.3f
        }
        else {
            button.isClickable = true
            button.isFocusable = true
            button.alpha = 1f
        }
    }
}

/**
 * Method used to pop up the WELL DONE text using and animator when the quiz state is COMPLETED
 * @param textView popup view
 * @param state the current state of the QUIZ
 */
@BindingAdapter("wellDonePopUp")
fun bindTextWD(textView: TextView, state: QuizState?) {
    state?.let {
        if (it == QuizState.CORRECT_ANSWERED) {
            textView.visibility = View.VISIBLE
            val objectAnimator = AnimatorInflater.loadAnimator(textView.context, R.animator.text_animation) as ObjectAnimator
            objectAnimator.target = textView
            objectAnimator.start()
        }
        else {
            if (textView.isVisible)
                textView.visibility = View.INVISIBLE
        }
    }
}
/**
 * Method used to pop up the TRY AGAIN text using and animator when the quiz state is TRY AGAIN
 * @param textView current TextView
 * @param state the current state of the QUIZ
 */
@BindingAdapter("tryAgainPopUp")
fun bindTextTA(textView: TextView, state: QuizState?) {
    state?.let {
        if (it == QuizState.WRONG_ANSWERED) {
            textView.visibility = View.VISIBLE
            val objectAnimator = AnimatorInflater.loadAnimator(textView.context, R.animator.text_animation) as ObjectAnimator
            objectAnimator.target = textView
            objectAnimator.start()
        }
        else {
            if (textView.isVisible)
                textView.visibility = View.INVISIBLE
        }
    }
}

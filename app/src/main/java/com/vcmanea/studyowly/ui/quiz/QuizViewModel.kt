package com.vcmanea.studyowly.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.quiz.QuizState
import com.vcmanea.studyowly.extensions.notifyObserver
import com.vcmanea.studyowly.repository.Repository
import timber.log.Timber

class QuizViewModel(val currentPosition: Int, repository: Repository) : ViewModel() {
    /////////////////////////////////////////////////////    QUIZ     ///////////////////////////////////////////////////////////////////
    //TUTORIAL SINGLETON
    private var _tutorial = repository.tutorial
    val tutorial: LiveData<Tutorial?>
        get() = _tutorial

    //QUIZ
    var _quiz = MutableLiveData<Quiz?>().apply {
        value = _tutorial.value?.getLessonByIndex(currentPosition) as Quiz
    }
    val quiz: LiveData<Quiz?>
        get() = _quiz

    init {
        Timber.d("init called")
    }


    fun onChoiceSelected(viewTag: String) {
        Timber.d("onChoiceSelected called ${viewTag}")
        _quiz.value?.selectChoice(viewTag)
        _quiz.notifyObserver()
    }


    fun onQuizButtonPressed() {
        //IF THE QUIZ IS ALREADY ANSWERED CORRECTLY MOVE TO NEXT
        if (quiz.value?.state == QuizState.CORRECT_ANSWERED) {
            nextPage()
            return
        }
        //IF THE QUIZ IS WRONGLY ANSWERED RESET THE QUIZ
        if(quiz.value?.state == QuizState.WRONG_ANSWERED) {
            _quiz.value?.tryAgain()
            _quiz.notifyObserver()
            return

        }
        //ELSE CHECK THE QUIZ CORRECTNESS
        _quiz.value?.checkAnswers()
        _quiz.notifyObserver()
    }


    //NEXT PAGE
    private val _nextPagerElement = MutableLiveData<Boolean?>()
    val nextPagerElement: LiveData<Boolean?>
        get() = _nextPagerElement
    fun nextPage() {
        _nextPagerElement.value = true
    }
    fun doneNextPage() {
        _nextPagerElement.value = null
    }


    //FACTORY
    @Suppress("UNCHECKED_CAST")
    class QuizViewModelFactory(private val currentPosition: Int, private val repo: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
                return QuizViewModel(currentPosition, repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
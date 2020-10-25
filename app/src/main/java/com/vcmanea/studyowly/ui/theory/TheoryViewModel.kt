package com.vcmanea.studyowly.ui.theory

import androidx.lifecycle.*
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.domain.tutorial.theory.Theory
import com.vcmanea.studyowly.extensions.notifyObserver
import com.vcmanea.studyowly.repository.Repository
import timber.log.Timber

class TheoryViewModel(private val currentPosition: Int, repository: Repository) :
    ViewModel() {

    //TUTORIAL SINGLETON
    private var _tutorial = repository.tutorial
    val tutorial: LiveData<Tutorial?>
        get() = _tutorial

    //THEORY
    var theory = Transformations.distinctUntilChanged(Transformations.map(tutorial) {
        it?.getLessonByIndex(currentPosition) as Theory
    })

    init {
        Timber.d("init called")
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



    @Suppress("UNCHECKED_CAST")
    class TheoryViewModelFactory(private val currentPosition: Int, private val repo: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(TheoryViewModel::class.java)) {
                return TheoryViewModel(currentPosition, repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
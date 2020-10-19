package com.vcmanea.studyowly.ui.tutorial

import androidx.lifecycle.*
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TutorialViewModel(private val chapterId: Long, repository: Repository) : ViewModel() {

    //Tutorial object
    private var _tutorial = MutableLiveData<Tutorial?>()

    val tutorial: LiveData<Tutorial?>
        get() = _tutorial


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tutorial.postValue(repository.getTutorial(chapterId))
            }
            Timber.d("The chapter id is : $chapterId")
        }
    }

    //PROGRESS BAR
    var progressMax = Transformations.distinctUntilChanged(Transformations.map(_tutorial) {
        it?.lessonsListSize
    })

    var currentProgress = Transformations.distinctUntilChanged(Transformations.map(_tutorial) {
        it?.progressBarIndex
    })

//    //PAGER
//    //Lesson List -> Pager
//    var unlockedElements = Transformations.distinctUntilChanged(Transformations.map(_tutorial) {
//        it?.unlockedElements
//    })
//
//    //Lesson List -> Pager
//    var lessonsList = Transformations.distinctUntilChanged(Transformations.map(_tutorial) {
//        it?.lessonList
//    })
//


//    fun pagerPositionChanged(position: Int) {
//        _tutorial.value?.progressBarIndex = position
//        _tutorial.notifyObserver()
//    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val chapterID: Long, val repository: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TutorialViewModel::class.java)) {
                return TutorialViewModel(chapterID, repository) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }

}



package com.vcmanea.studyowly.ui.tutorial

import androidx.lifecycle.*
import com.vcmanea.studyowly.domain.tutorial.Tutorial
import com.vcmanea.studyowly.extensions.notifyObserver
import com.vcmanea.studyowly.repository.Repository

class TutorialViewModel(private val chapterId: Long, repository: Repository) : ViewModel() {

    //VERY VERY IMPORTANT AS THE CHAPTER TO BE FULLY DOWNLOADED BEFORE INITIALISING THE VIEWPAGE
    private var _tutorialUpdated =MutableLiveData<Boolean?>()
    val tutorialUpdated: LiveData<Boolean?>
        get() = _tutorialUpdated

    //TUTORIAL
    private var _tutorial =repository.tutorial
    val tutorial: LiveData<Tutorial?>
        get() = _tutorial

    //PROGRESS BAR
    var progressMax =repository.progressMax
    var currentProgress = repository.currentProgress
    //PAGER
    var lessonsList = repository.lessonsList

    fun pagerPositionChanged(position: Int) {
        _tutorial.value?.progressBarIndex = position
        _tutorial.notifyObserver()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val chapterID: Long, val repository: Repository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TutorialViewModel::class.java)) {
                return TutorialViewModel(chapterID, repository) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}



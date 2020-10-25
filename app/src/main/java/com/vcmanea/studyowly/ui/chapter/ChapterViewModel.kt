package com.vcmanea.studyowly.ui.chapter

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.vcmanea.studyowly.repository.Repository
import kotlinx.coroutines.*

class ChapterViewModel @ViewModelInject constructor (private val repository: Repository) : ViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
               repository.refreshKotlinFirebase()
            }
        }
    }

    var chapterList= repository.chaptersLD

    //*****************************************NAVIGATE****************************************************//
    //Navigate for Course to Chapter
    private val _navigateToTutorial = MutableLiveData<Long>()
    val navigateToTutorial: LiveData<Long>
        get() = _navigateToTutorial

    //NAVIGATE TO TUTORIAL ONLY AFTER THE TUTORIAL IS COMPLETELY DOWNLOADED
    fun displayTutorial(chapterId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
              repository.setTutorial(chapterId)
            }
            _navigateToTutorial.postValue(chapterId)
        }
    }

    //We need to clean the LiveData to avoid it being triggered again when we return from the DetailView
    fun displayTutorialComplete() {
        _navigateToTutorial.value = null
    }

    fun updateChapter(chapterId: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.completeChapter(chapterId)
            }
        }
    }

}
package com.vcmanea.studyowly.extensions

import androidx.lifecycle.MutableLiveData
/**
Observe when data
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
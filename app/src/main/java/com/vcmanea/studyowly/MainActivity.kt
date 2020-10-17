package com.vcmanea.studyowly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vcmanea.studyowly.firebase.FirebaseDB
import com.vcmanea.studyowly.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    @Inject lateinit var repository:Repository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModelJob = Job()
        //UI scope
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        uiScope.launch {
            withContext(Dispatchers.IO) {
               repository.refreshKotlinFirebase()
               repository.getTutorial(1)
            }
        }
    }
}
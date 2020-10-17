package com.vcmanea.studyowly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vcmanea.studyowly.firebase.FirebaseDB
import com.vcmanea.studyowly.repository.Repository
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity(),FirebaseDB.OnDownloadComplete {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         val viewModelJob= Job()
        //UI scope
         val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

        uiScope.launch {
            withContext(Dispatchers.IO){
                Repository.refreshKotlinFirebase()
                Repository.getTutorial(1)
            }
        }


    }

    override fun onDownloadComplete() {
       Timber.d("download completed")
    }


}
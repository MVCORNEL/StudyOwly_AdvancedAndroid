package com.vcmanea.studyowly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vcmanea.studyowly.firebase.FirebaseDB
import timber.log.Timber

class MainActivity : AppCompatActivity(),FirebaseDB.OnDownloadComplete {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebaseDB=FirebaseDB.getInstance(this)
        firebaseDB.downloadData()

    }

    override fun onDownloadComplete() {
       Timber.d("download completed")
    }
}
package com.vcmanea.studyowly.application

import android.app.Application
import android.content.Context
import timber.log.Timber


class MyApplication : Application() {

    companion object {
        lateinit var context: Context

        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        context=applicationContext
    }

}
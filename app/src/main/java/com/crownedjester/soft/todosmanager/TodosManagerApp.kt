package com.crownedjester.soft.todosmanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodosManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules()
            androidContext(this@TodosManagerApp)
            androidLogger()
        }

    }


}
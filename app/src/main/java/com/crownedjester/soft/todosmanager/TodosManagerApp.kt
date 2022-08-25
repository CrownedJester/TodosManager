package com.crownedjester.soft.todosmanager

import android.app.Application
import com.crownedjester.soft.todosmanager.di.module
import com.crownedjester.soft.todosmanager.di.presenterModule
import com.crownedjester.soft.todosmanager.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodosManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(module, useCasesModule, presenterModule))
            androidContext(this@TodosManagerApp)
            androidLogger()
        }

    }


}
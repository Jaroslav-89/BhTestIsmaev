package com.jaroapps.server.app

import android.app.Application
import com.jaroapps.server.di.dataModule
import com.jaroapps.server.di.interactorModule
import com.jaroapps.server.di.repositoryModule
import com.jaroapps.server.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}
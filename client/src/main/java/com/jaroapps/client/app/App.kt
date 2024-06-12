package com.jaroapps.client.app

import android.app.Application
import com.jaroapps.client.di.dataModule
import com.jaroapps.client.di.interactorModule
import com.jaroapps.client.di.repositoryModule
import com.jaroapps.client.di.viewModelModule
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
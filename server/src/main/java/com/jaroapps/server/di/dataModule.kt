package com.jaroapps.server.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.jaroapps.server.data.db.AppDataBase
import com.jaroapps.server.data.network.KtorNetworkServer
import com.jaroapps.server.data.network.NetworkServer
import com.jaroapps.server.data.storage.ConfigStorage
import com.jaroapps.server.data.storage.shared_prefs.ConfigStorageSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val dataModule = module {

    single<ConfigStorage> {
        ConfigStorageSharedPrefs(
            sharedPrefs = get(),
            gson = get()
        )
    }

    factory {
        Gson()
    }

    single {
        androidContext().getSharedPreferences(
            ConfigStorageSharedPrefs.CONFIG_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    single<NetworkServer> {
        KtorNetworkServer(
            dataBase = get(),
            gson = get(),
        )
    }

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
package com.jaroapps.client.di

import android.content.Context
import com.google.gson.Gson
import com.jar89.playlistmaker.sharing.data.navigation.ExternalNavigator
import com.jaroapps.client.data.network.KtorNetworkClient
import com.jaroapps.client.data.network.NetworkClient
import com.jaroapps.client.data.remote_control.RemoteControlService
import com.jaroapps.client.data.storage.ClientStorage
import com.jaroapps.client.data.storage.shared_prefs.ClientStorageSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val dataModule = module {
    single<ClientStorage> {
        ClientStorageSharedPrefs(
            sharedPrefs = get(),
            gson = get(),
        )
    }

    factory {
        Gson()
    }

    single {
        androidContext().getSharedPreferences(
            ClientStorageSharedPrefs.CLIENT_SETTINGS,
            Context.MODE_PRIVATE
        )
    }

    single<NetworkClient> {
        KtorNetworkClient(
            storage = get(),
            gson = get(),
        )
    }

    single {
        ExternalNavigator(context = androidContext())
    }

    single {
        RemoteControlService()
    }
}
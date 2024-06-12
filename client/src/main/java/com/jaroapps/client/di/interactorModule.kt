package com.jaroapps.client.di

import com.jaroapps.client.domain.api.MainInteractor
import com.jaroapps.client.domain.impl.MainInteractorImpl
import org.koin.dsl.module

internal val interactorModule = module {
    single<MainInteractor> {
        MainInteractorImpl(clientRepository = get())
    }
}
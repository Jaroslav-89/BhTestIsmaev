package com.jaroapps.server.di

import com.jaroapps.server.domain.api.LogUseCase
import com.jaroapps.server.domain.api.MainInteractor
import com.jaroapps.server.domain.impl.LogUseCaseImpl
import com.jaroapps.server.domain.impl.MainInteractorImpl
import org.koin.dsl.module

internal val interactorModule = module {
    single<MainInteractor> {
        MainInteractorImpl(serverRepository = get())
    }

    single<LogUseCase> {
        LogUseCaseImpl(serverRepository = get())
    }
}
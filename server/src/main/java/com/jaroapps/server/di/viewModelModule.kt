package com.jaroapps.server.di

import com.jaroapps.server.presentation.viewmodel.LogViewModel
import com.jaroapps.server.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::MainViewModel)

    viewModelOf(::LogViewModel)
}
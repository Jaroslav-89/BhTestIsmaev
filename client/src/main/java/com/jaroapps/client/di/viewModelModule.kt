package com.jaroapps.client.di

import com.jaroapps.client.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { MainViewModel(mainInteractor = get()) }
}
package com.jaroapps.server.presentation.state

internal sealed interface MainScreenState {
    data object Loading : MainScreenState
    data class Value(val isStarted: Boolean) : MainScreenState
}
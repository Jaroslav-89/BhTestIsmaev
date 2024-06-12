package com.jaroapps.server.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaroapps.server.domain.api.LogUseCase
import com.jaroapps.server.presentation.state.LogScreenState
import kotlinx.coroutines.launch

internal class LogViewModel(
    private val logUseCase: LogUseCase
) : ViewModel() {
    private val _screenState = MutableLiveData<LogScreenState>(LogScreenState.Loading)
    val screenState: LiveData<LogScreenState>
        get() = _screenState

    init {
        viewModelScope.launch {
            logUseCase.getLogs().collect() {
                _screenState.postValue(LogScreenState.Content(it))
            }
        }
    }
}
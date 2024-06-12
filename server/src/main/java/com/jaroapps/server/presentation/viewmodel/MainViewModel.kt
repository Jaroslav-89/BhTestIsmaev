package com.jaroapps.server.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaroapps.server.domain.api.MainInteractor
import com.jaroapps.server.domain.model.Config
import com.jaroapps.server.presentation.state.ConfigState
import com.jaroapps.server.presentation.state.MainScreenState
import com.jaroapps.server.utills.debounce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    private var isStarted = false
    private var serverJob: Job? = null
    private var isClickAllowed = true
    private val startStopServerClickDebounce = debounce<Boolean>(
        CLICK_DEBOUNCE_DELAY,
        viewModelScope,
        false
    ) { param ->
        isClickAllowed = param
    }

    private val _screenState = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val screenState: LiveData<MainScreenState>
        get() = _screenState

    private val _configState = MutableLiveData<ConfigState>()
    val configState: LiveData<ConfigState>
        get() = _configState

    init {
        checkServerIsStarted()
        getConfig()
    }

    private fun checkServerIsStarted() {
        viewModelScope.launch(Dispatchers.IO) {
            isStarted = mainInteractor.checkServerIsStarted()
            _screenState.postValue(MainScreenState.Value(isStarted))
        }
    }

    fun startStopBtnClicked() {
        if (clickDebounce()) {
            startStopServer()
        }
    }

    private fun startStopServer() {
        _screenState.postValue(MainScreenState.Loading)
        if (isStarted) {
            stopServer()
        } else {
            startServer()
        }
    }

    private fun startServer() {
        if (serverJob == null) {
            serverJob = viewModelScope.launch(Dispatchers.IO) {
                mainInteractor.start() {
                    checkServerIsStarted()
                }
            }
        }
    }

    private fun stopServer() {
        viewModelScope.launch(Dispatchers.IO) {
            mainInteractor.stop() {
                checkServerIsStarted()
            }
            serverJob?.cancel()
            serverJob = null
        }
    }

    fun getConfig() {
        viewModelScope.launch {
            _configState.postValue(ConfigState.Content(mainInteractor.getConfig()))
        }
    }

    fun saveConfig(port: String) {
        viewModelScope.launch {
            mainInteractor.saveConfig(Config(port = port))
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            startStopServerClickDebounce(true)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
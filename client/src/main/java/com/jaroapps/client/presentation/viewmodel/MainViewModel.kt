package com.jaroapps.client.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaroapps.client.domain.api.MainInteractor
import com.jaroapps.client.domain.model.Config
import com.jaroapps.client.presentation.state.ConfigState
import com.jaroapps.client.presentation.state.MainScreenState
import com.jaroapps.server.utills.debounce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    private var isConnected = false
    private var connectionJob: Job? = null
    private var isClickAllowed = true
    private val startPauseClickDebounce = debounce<Boolean>(
        CLICK_DEBOUNCE_DELAY,
        viewModelScope,
        false
    ) { param ->
        isClickAllowed = param
    }

    private val _screenState = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val screenState: LiveData<MainScreenState>
        get() = _screenState

    private val _configState = MutableLiveData<ConfigState>(ConfigState.Default)
    val configState: LiveData<ConfigState>
        get() = _configState

    init {
        checkConnectStatus()
        getConfig()
    }

    fun checkConnectStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            isConnected = mainInteractor.checkConnectStatus()
            _screenState.postValue(MainScreenState.Value(isConnected))
        }
    }

    fun startPauseBtnClicked() {
        if (clickDebounce()) {
            startStopConnect()
        }
    }

    private fun startStopConnect() {
        _screenState.postValue(MainScreenState.Loading)
        if (isConnected) {
            stopServer()
        } else {
            startServer()
        }
    }

    private fun startServer() {
        if (connectionJob == null) {
            connectionJob = viewModelScope.launch(Dispatchers.IO) {
                mainInteractor.connect() {
                    checkConnectStatus()
                }
            }
        }
    }

    private fun stopServer() {
        viewModelScope.launch(Dispatchers.IO) {
            mainInteractor.disconnect() {
                checkConnectStatus()
            }
            connectionJob?.cancel()
            connectionJob = null
        }
    }

    fun getConfig() {
        viewModelScope.launch {
            _configState.postValue(ConfigState.Content(mainInteractor.getConfig()))
        }
    }

    fun saveConfig(ip: String, port: String) {
        viewModelScope.launch {
            mainInteractor.saveConfig(Config(ip = ip, port = port))
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            startPauseClickDebounce(true)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
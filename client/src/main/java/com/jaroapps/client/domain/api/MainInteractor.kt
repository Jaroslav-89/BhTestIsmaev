package com.jaroapps.client.domain.api

import com.jaroapps.client.domain.model.Config

internal interface MainInteractor {
    suspend fun connect(completion: () -> Unit)
    suspend fun disconnect(completion: () -> Unit)
    suspend fun checkConnectStatus(): Boolean
    suspend fun getConfig(): Config
    suspend fun saveConfig(config: Config)
    suspend fun openBrowser()
}
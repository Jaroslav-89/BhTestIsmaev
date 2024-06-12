package com.jaroapps.server.domain.api

import com.jaroapps.server.domain.model.Config

internal interface MainInteractor {
    suspend fun checkServerIsStarted(): Boolean
    suspend fun start(completion: () -> Unit)
    suspend fun stop(completion: () -> Unit)
    suspend fun getConfig(): Config
    suspend fun saveConfig(config: Config)
}
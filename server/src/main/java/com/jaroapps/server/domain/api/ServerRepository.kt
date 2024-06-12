package com.jaroapps.server.domain.api

import com.jaroapps.server.domain.model.Config
import com.jaroapps.server.domain.model.ServerLog
import kotlinx.coroutines.flow.Flow

internal interface ServerRepository {
    suspend fun checkServerIsStarted(): Boolean
    suspend fun start(completion: () -> Unit)
    suspend fun stop(completion: () -> Unit)
    suspend fun getLogs(): Flow<List<ServerLog>>
    suspend fun getConfig(): Config
    suspend fun saveConfig(config: Config)
}
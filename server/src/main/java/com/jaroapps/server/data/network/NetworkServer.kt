package com.jaroapps.server.data.network

import com.jaroapps.server.data.dto.ConfigDto

internal interface NetworkServer {
    suspend fun start(config: ConfigDto, completion: () -> Unit)
    suspend fun stop(completion: () -> Unit)
    suspend fun isStarted(): Boolean
}
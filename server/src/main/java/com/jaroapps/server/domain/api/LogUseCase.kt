package com.jaroapps.server.domain.api

import com.jaroapps.server.domain.model.ServerLog
import kotlinx.coroutines.flow.Flow

internal interface LogUseCase {
    suspend fun getLogs(): Flow<List<ServerLog>>
}
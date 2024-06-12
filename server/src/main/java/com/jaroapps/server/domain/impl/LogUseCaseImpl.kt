package com.jaroapps.server.domain.impl

import com.jaroapps.server.domain.api.LogUseCase
import com.jaroapps.server.domain.api.ServerRepository
import com.jaroapps.server.domain.model.ServerLog
import kotlinx.coroutines.flow.Flow

internal class LogUseCaseImpl(
    private val serverRepository: ServerRepository
) : LogUseCase {
    override suspend fun getLogs(): Flow<List<ServerLog>> {
        return serverRepository.getLogs()
    }
}
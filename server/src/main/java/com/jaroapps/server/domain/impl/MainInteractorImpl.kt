package com.jaroapps.server.domain.impl

import com.jaroapps.server.domain.api.MainInteractor
import com.jaroapps.server.domain.api.ServerRepository
import com.jaroapps.server.domain.model.Config

internal class MainInteractorImpl(
    private val serverRepository: ServerRepository
) : MainInteractor {

    override suspend fun checkServerIsStarted(): Boolean {
        return serverRepository.checkServerIsStarted()
    }

    override suspend fun start(completion: () -> Unit) {
        serverRepository.start(completion)
    }

    override suspend fun stop(completion: () -> Unit) {
        serverRepository.stop(completion)
    }

    override suspend fun getConfig(): Config {
        return serverRepository.getConfig()
    }

    override suspend fun saveConfig(config: Config) {
        serverRepository.saveConfig(config)
    }
}
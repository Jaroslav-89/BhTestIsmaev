package com.jaroapps.client.domain.impl

import com.jaroapps.client.domain.api.ClientRepository
import com.jaroapps.client.domain.api.MainInteractor
import com.jaroapps.client.domain.model.Config

internal class MainInteractorImpl(
    private val clientRepository: ClientRepository,
) : MainInteractor {
    override suspend fun connect(completion: () -> Unit) {
        clientRepository.connect(completion)
    }

    override suspend fun disconnect(completion: () -> Unit) {
        clientRepository.disconnect(completion)
    }

    override suspend fun checkConnectStatus(): Boolean {
        return clientRepository.checkConnectStatus()
    }

    override suspend fun getConfig(): Config {
        return clientRepository.getConfig()
    }

    override suspend fun saveConfig(config: Config) {
        clientRepository.saveConfig(config)
    }

    override suspend fun openBrowser() {
        clientRepository.openBrowser()
    }
}
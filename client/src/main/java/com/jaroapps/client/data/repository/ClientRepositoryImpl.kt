package com.jaroapps.client.data.repository

import com.jar89.playlistmaker.sharing.data.navigation.ExternalNavigator
import com.jaroapps.client.data.network.NetworkClient
import com.jaroapps.client.data.storage.ClientStorage
import com.jaroapps.client.domain.api.ClientRepository
import com.jaroapps.client.domain.model.Config
import com.jaroapps.server.data.converters.ConfigDtoConvertor

internal class ClientRepositoryImpl(
    private val externalNavigator: ExternalNavigator,
    private val networkClient: NetworkClient,
    private val storage: ClientStorage,
    private val configConvertor: ConfigDtoConvertor,
) : ClientRepository {
    override suspend fun connect(completion: () -> Unit) {
        openBrowser()
        networkClient.connect(completion)

    }

    override suspend fun disconnect(completion: () -> Unit) {
        networkClient.disconnect(completion)
    }

    override suspend fun checkConnectStatus(): Boolean {
        return networkClient.isConnected()
    }

    override suspend fun getConfig(): Config {
        return configConvertor.mapDtoToConfig(storage.getConfig())
    }

    override suspend fun saveConfig(config: Config) {
        storage.saveConfig(configConvertor.mapConfigToDto(config))
    }

    override suspend fun openBrowser() {
        externalNavigator.openBrowser()
    }
}
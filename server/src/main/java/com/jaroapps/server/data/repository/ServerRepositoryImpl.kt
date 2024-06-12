package com.jaroapps.server.data.repository

import com.jaroapps.server.data.converters.ConfigDtoConvertor
import com.jaroapps.server.data.converters.ServerLogDbConvertor
import com.jaroapps.server.data.db.AppDataBase
import com.jaroapps.server.data.db.entity.SwipeLogEntity
import com.jaroapps.server.data.network.NetworkServer
import com.jaroapps.server.data.storage.ConfigStorage
import com.jaroapps.server.domain.api.ServerRepository
import com.jaroapps.server.domain.model.Config
import com.jaroapps.server.domain.model.ServerLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ServerRepositoryImpl(
    private val storage: ConfigStorage,
    private val server: NetworkServer,
    private val configConvertor: ConfigDtoConvertor,
    private val dataBase: AppDataBase,
    private val serverLogDbConvertor: ServerLogDbConvertor,
) : ServerRepository {

    override suspend fun checkServerIsStarted(): Boolean {
        return server.isStarted()
    }

    override suspend fun start(completion: () -> Unit) {
        server.start(storage.getConfig(), completion)
    }

    override suspend fun stop(completion: () -> Unit) {
        server.stop(completion)
    }

    override suspend fun getLogs(): Flow<List<ServerLog>> {
        return dataBase.logDao().getClientsSwipeLogs()
            .map { value: List<SwipeLogEntity> ->
                value.map {
                    serverLogDbConvertor.mapEntityToServerLog(
                        it
                    )
                }
            }
    }

    override suspend fun getConfig(): Config {
        return configConvertor.mapDtoToConfig(storage.getConfig())
    }

    override suspend fun saveConfig(config: Config) {
        storage.saveConfig(configConvertor.mapConfigToDto(config))
    }
}
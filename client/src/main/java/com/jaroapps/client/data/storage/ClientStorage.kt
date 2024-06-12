package com.jaroapps.client.data.storage

import com.jaroapps.client.data.dto.ClientIdDto
import com.jaroapps.client.data.dto.ConfigDto

internal interface ClientStorage {
    suspend fun getConfig(): ConfigDto
    suspend fun getClientId(): ClientIdDto
    suspend fun saveConfig(configDto: ConfigDto)
    suspend fun saveClientId(clientIdDto: ClientIdDto)
}
package com.jaroapps.server.data.storage

import com.jaroapps.server.data.dto.ConfigDto

internal interface ConfigStorage {
    suspend fun getConfig(): ConfigDto
    suspend fun saveConfig(configDto: ConfigDto)
}
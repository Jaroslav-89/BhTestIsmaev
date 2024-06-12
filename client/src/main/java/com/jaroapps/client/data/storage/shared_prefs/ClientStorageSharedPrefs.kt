package com.jaroapps.client.data.storage.shared_prefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jaroapps.client.data.dto.ClientIdDto
import com.jaroapps.client.data.dto.ConfigDto
import com.jaroapps.client.data.storage.ClientStorage

internal class ClientStorageSharedPrefs(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
) : ClientStorage {

    override suspend fun getConfig(): ConfigDto {
        return readConfigFromJson()
    }

    override suspend fun getClientId(): ClientIdDto {
        return readClientIdFromJson()
    }

    override suspend fun saveConfig(configDto: ConfigDto) {
        writeConfigToJson(configDto)
    }

    override suspend fun saveClientId(clientIdDto: ClientIdDto) {
        writeClientIdToJson(clientIdDto)
    }

    private fun readConfigFromJson(): ConfigDto {
        val json = sharedPrefs.getString(CONFIG_KEY, null) ?: return ConfigDto(
            ip = "",
            port = ""
        )
        return gson.fromJson(json, ConfigDto::class.java)
    }

    private fun writeConfigToJson(configDto: ConfigDto) {
        val json = gson.toJson(configDto)
        sharedPrefs.edit()
            .putString(CONFIG_KEY, json)
            .apply()
    }

    private fun readClientIdFromJson(): ClientIdDto {
        val json = sharedPrefs.getString(ID_KEY, null) ?: return ClientIdDto(clientId = "")
        return gson.fromJson(json, ClientIdDto::class.java)
    }

    private fun writeClientIdToJson(clientIdDto: ClientIdDto) {
        val json = gson.toJson(clientIdDto)
        sharedPrefs.edit()
            .putString(ID_KEY, json)
            .apply()
    }

    companion object {
        const val CLIENT_SETTINGS = "client_settings"
        const val CONFIG_KEY = "client_config_key"
        const val ID_KEY = "client_id_key"
    }
}
package com.jaroapps.server.data.storage.shared_prefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jaroapps.server.data.dto.ConfigDto
import com.jaroapps.server.data.storage.ConfigStorage

internal class ConfigStorageSharedPrefs(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
) : ConfigStorage {
    override suspend fun getConfig(): ConfigDto {
        return readConfigFromJson()
    }

    override suspend fun saveConfig(configDto: ConfigDto) {
        writeConfigToJson(configDto)
    }

    private fun readConfigFromJson(): ConfigDto {
        val json = sharedPrefs.getString(CONFIG_KEY, null) ?: return ConfigDto(port = "")
        return gson.fromJson(json, ConfigDto::class.java)
    }

    private fun writeConfigToJson(configDto: ConfigDto) {
        val json = gson.toJson(configDto)
        sharedPrefs.edit()
            .putString(CONFIG_KEY, json)
            .apply()
    }

    companion object {
        const val CONFIG_PREFERENCES = "server_config"
        const val CONFIG_KEY = "config_key"
    }
}
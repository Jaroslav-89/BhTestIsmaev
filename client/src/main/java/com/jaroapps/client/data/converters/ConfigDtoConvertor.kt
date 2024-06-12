package com.jaroapps.server.data.converters

import com.jaroapps.client.data.dto.ConfigDto
import com.jaroapps.client.domain.model.Config

internal class ConfigDtoConvertor {
    fun mapDtoToConfig(dto: ConfigDto): Config {
        return Config(
            ip = dto.ip,
            port = dto.port,
        )
    }

    fun mapConfigToDto(config: Config): ConfigDto {
        return ConfigDto(
            ip = config.ip,
            port = config.port,
        )
    }
}
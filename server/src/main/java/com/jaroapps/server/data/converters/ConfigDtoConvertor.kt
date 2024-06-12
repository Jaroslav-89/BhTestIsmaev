package com.jaroapps.server.data.converters

import com.jaroapps.server.data.dto.ConfigDto
import com.jaroapps.server.domain.model.Config

internal class ConfigDtoConvertor {
    fun mapDtoToConfig(dto: ConfigDto): Config {
        return Config(
            port = dto.port,
        )
    }

    fun mapConfigToDto(config: Config): ConfigDto {
        return ConfigDto(
            port = config.port,
        )
    }
}
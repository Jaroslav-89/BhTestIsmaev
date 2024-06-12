package com.jaroapps.server.di

import com.jaroapps.server.data.converters.ConfigDtoConvertor
import com.jaroapps.server.data.converters.ServerLogDbConvertor
import com.jaroapps.server.data.repository.ServerRepositoryImpl
import com.jaroapps.server.domain.api.ServerRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<ServerRepository> {
        ServerRepositoryImpl(
            storage = get(),
            server = get(),
            configConvertor = get(),
            dataBase = get(),
            serverLogDbConvertor = get()
        )
    }

    single {
        ConfigDtoConvertor()
    }

    single {
        ServerLogDbConvertor()
    }
}
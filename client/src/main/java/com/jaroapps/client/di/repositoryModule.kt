package com.jaroapps.client.di

import com.jaroapps.client.data.repository.ClientRepositoryImpl
import com.jaroapps.client.domain.api.ClientRepository
import com.jaroapps.server.data.converters.ConfigDtoConvertor
import org.koin.dsl.module

internal val repositoryModule = module {
    single<ClientRepository> {
        ClientRepositoryImpl(
            externalNavigator = get(),
            networkClient = get(),
            storage = get(),
            configConvertor = get(),
        )
    }

    single {
        ConfigDtoConvertor()
    }
}
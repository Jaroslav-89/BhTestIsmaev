package com.jaroapps.client.presentation.state

import com.jaroapps.client.domain.model.Config

internal sealed interface ConfigState {
    data object Default : ConfigState
    data class Content(val config: Config) : ConfigState
}
package com.jaroapps.server.presentation.state

import com.jaroapps.server.domain.model.Config

internal sealed interface ConfigState {
    data object Default : ConfigState
    data class Content(val config: Config) : ConfigState
}
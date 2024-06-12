package com.jaroapps.server.presentation.state

import com.jaroapps.server.domain.model.ServerLog

internal sealed interface LogScreenState {
    data object Loading : LogScreenState
    data class Content(val serverLog: List<ServerLog>) : LogScreenState
}
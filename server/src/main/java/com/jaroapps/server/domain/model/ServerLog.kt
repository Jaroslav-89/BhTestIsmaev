package com.jaroapps.server.domain.model

internal data class ServerLog(
    val clientId: String,
    val swipeDirection: String,
    val swipeValue: String,
    val addTime: Long,
)

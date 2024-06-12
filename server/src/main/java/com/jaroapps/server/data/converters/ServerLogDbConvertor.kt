package com.jaroapps.server.data.converters

import com.jaroapps.server.data.db.entity.SwipeLogEntity
import com.jaroapps.server.domain.model.ServerLog

internal class ServerLogDbConvertor {
    fun mapEntityToServerLog(entity: SwipeLogEntity): ServerLog {
        return ServerLog(
            clientId = entity.clientId,
            swipeDirection = entity.swipeDirection,
            swipeValue = entity.swipeValue,
            addTime = entity.addTime,
        )
    }
}
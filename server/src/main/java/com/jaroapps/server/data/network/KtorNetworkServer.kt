package com.jaroapps.server.data.network

import com.google.gson.Gson
import com.jaroapps.server.data.db.AppDataBase
import com.jaroapps.server.data.db.entity.SwipeLogEntity
import com.jaroapps.server.data.dto.ClientIdDto
import com.jaroapps.server.data.dto.ConfigDto
import com.jaroapps.server.data.dto.SwipeDto
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

internal class KtorNetworkServer(
    private val dataBase: AppDataBase,
    private val gson: Gson,
) : NetworkServer {
    private var server: NettyApplicationEngine? = null
    private var isStarted = false

    override suspend fun start(config: ConfigDto, completion: () -> Unit) {

        server = embeddedServer(Netty, port = config.port.toInt()) {
            install(WebSockets)
            isStarted = true
            completion()

            routing {

                webSocket("/") {
                    var clientId = UUID.randomUUID().toString()

                    var sendSwipeJob: Job? = null
                    try {
                        send(gson.toJson(ClientIdDto(clientId)))

                        for (frame in incoming) {
                            when (frame) {
                                is Frame.Text -> {
                                    val text = frame.readText()
                                    when {
                                        text.contains(CLIENT_ID) -> {
                                            val confirmedClientId = gson.fromJson(
                                                text,
                                                ClientIdDto::class.java
                                            ).clientId
                                            clientId = confirmedClientId

                                            sendSwipeJob = launch {
                                                while (true) {
                                                    send(
                                                        Frame.Text(
                                                            gson.toJson(
                                                                SwipeDto(
                                                                    swipeDirection = "up",
                                                                    swipeValue = "100"
                                                                )
                                                            )
                                                        )
                                                    )
                                                    delay(500)
                                                    send(
                                                        Frame.Text(
                                                            gson.toJson(
                                                                SwipeDto(
                                                                    swipeDirection = "down",
                                                                    swipeValue = "100"
                                                                )
                                                            )
                                                        )
                                                    )
                                                    delay(500)
                                                    send(
                                                        Frame.Text(
                                                            gson.toJson(
                                                                SwipeDto(
                                                                    swipeDirection = "up",
                                                                    swipeValue = "200"
                                                                )
                                                            )
                                                        )
                                                    )
                                                    delay(500)
                                                    send(
                                                        Frame.Text(
                                                            gson.toJson(
                                                                SwipeDto(
                                                                    swipeDirection = "down",
                                                                    swipeValue = "200"
                                                                )
                                                            )
                                                        )
                                                    )
                                                    delay(500)
                                                }
                                            }
                                        }

                                        text.contains(SWIPE_LOG) -> {
                                            val clientLog =
                                                gson.fromJson(text, SwipeDto::class.java)
                                            val newSwipeLog = SwipeLogEntity(
                                                id = 0,
                                                clientId = clientId,
                                                swipeDirection = clientLog.swipeDirection,
                                                swipeValue = clientLog.swipeValue,
                                                addTime = System.currentTimeMillis(),
                                            )
                                            dataBase.logDao().insertClientSwipeLog(newSwipeLog)
                                        }

                                        else -> {}
                                    }
                                }

                                is Frame.Close -> {
                                    sendSwipeJob?.cancel()
                                    sendSwipeJob = null
                                }

                                else -> {}
                            }
                        }
                    } catch (e: Exception) {
                        println(e.localizedMessage)
                    }
                }
            }
        }
        server?.start(wait = true)
    }

    override suspend fun stop(completion: () -> Unit) {
        try {
            isStarted = false
            server?.stop(100, 100)
            server = null
        } catch (e: Exception) {
        }

        completion()
    }

    override suspend fun isStarted(): Boolean {
        return isStarted
    }

    companion object {
        private const val CLIENT_ID = "clientId"
        private const val SWIPE_LOG = "swipe"
    }
}
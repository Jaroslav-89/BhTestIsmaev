package com.jaroapps.client.data.network

import com.google.gson.Gson
import com.jaroapps.client.data.dto.ClientIdDto
import com.jaroapps.client.data.dto.SwipeDto
import com.jaroapps.client.data.remote_control.RemoteControlService
import com.jaroapps.client.data.storage.ClientStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

internal class KtorNetworkClient(
    private val storage: ClientStorage,
    private val gson: Gson,
) : NetworkClient {

    private var httpClient: HttpClient? = null
    private var isConnected = false

    override suspend fun connect(completion: () -> Unit): String {
        var clientId = storage.getClientId().clientId
        val configDto = storage.getConfig()

        httpClient = HttpClient {
            install(WebSockets)
        }
        httpClient?.webSocket(
            method = HttpMethod.Get,
            host = configDto.ip,
            port = configDto.port.toInt(),
            path = "/"
        ) {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        when {
                            text.contains(CLIENT_ID) -> {
                                isConnected = true
                                completion()
                                val newId = gson.fromJson(text, ClientIdDto::class.java).clientId
                                if (clientId.isBlank()) {
                                    clientId = newId
                                    storage.saveClientId(ClientIdDto(clientId))
                                }
                                send(Frame.Text(gson.toJson(ClientIdDto(clientId))))
                            }

                            text.contains(SWIPE) -> {
                                val swipeDto = gson.fromJson(text, SwipeDto::class.java)

                                RemoteControlService.instance?.performSwipe(swipeDto) {
                                    launch {
                                        send(Frame.Text(gson.toJson(swipeDto)))
                                    }
                                }
                            }
                        }
                    }

                    is Frame.Close -> {
                        isConnected = false
                        httpClient?.cancel()
                        httpClient = null
                        completion()
                    }

                    else -> {}
                }
            }
        }
        return clientId
    }

    override suspend fun disconnect(completion: () -> Unit) {
        isConnected = false
        httpClient?.cancel()
        httpClient = null
        completion()
    }

    override suspend fun isConnected(): Boolean {
        return isConnected
    }

    companion object {
        private const val CLIENT_ID = "clientId"
        private const val SWIPE = "swipe"
    }
}
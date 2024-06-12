package com.jaroapps.client.data.network

internal interface NetworkClient {
    suspend fun connect(completion: () -> Unit): String
    suspend fun disconnect(completion: () -> Unit)
    suspend fun isConnected(): Boolean
}
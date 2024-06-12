package com.jaroapps.server.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "swipe_log_table")
internal data class SwipeLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val clientId: String,
    val swipeDirection: String,
    val swipeValue: String,
    val addTime: Long,
)

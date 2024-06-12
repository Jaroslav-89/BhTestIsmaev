package com.jaroapps.server.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaroapps.server.data.db.entity.SwipeLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SwipeLogDao {
    @Insert(entity = SwipeLogEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClientSwipeLog(swipeLog: SwipeLogEntity)

    @Query("SELECT * FROM swipe_log_table ORDER BY addTime DESC")
    fun getClientsSwipeLogs(): Flow<List<SwipeLogEntity>>
}
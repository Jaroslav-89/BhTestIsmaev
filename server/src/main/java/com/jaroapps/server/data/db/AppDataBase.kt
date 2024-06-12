package com.jaroapps.server.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaroapps.server.data.db.dao.SwipeLogDao
import com.jaroapps.server.data.db.entity.SwipeLogEntity

@Database(
    version = 3,
    entities = [
        SwipeLogEntity::class
    ],
    exportSchema = false
)
internal abstract class AppDataBase : RoomDatabase() {

    abstract fun logDao(): SwipeLogDao
}
package com.imthiyas.geofencetracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GeofenceEntity::class, VisitEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): GeofenceDao

    companion object {
        lateinit var INSTANCE: AppDatabase
        fun init(context: Context) {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "geo_db"
            ).build()
        }
    }
}

package com.imthiyas.geofencetracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GeofenceDao {

    @Insert
    suspend fun insertGeofence(entity: GeofenceEntity)

    @Insert
    suspend fun insertVisit(entity: VisitEntity)

    @Query("SELECT * FROM geofences")
    suspend fun getAllGeofences(): List<GeofenceEntity>

    @Query("SELECT * FROM visits ORDER BY entryTime DESC")
    suspend fun getAllVisits(): List<VisitEntity>
}

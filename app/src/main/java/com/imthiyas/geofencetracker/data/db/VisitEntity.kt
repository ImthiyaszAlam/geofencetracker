package com.imthiyas.geofencetracker.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class VisitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val geofenceId: Int,
    val geofenceName: String,
    val entryTime: Long,
    val exitTime: Long,
    val duration: Long
)

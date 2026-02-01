package com.imthiyas.geofencetracker

import android.app.Application
import com.imthiyas.geofencetracker.data.db.AppDatabase

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}
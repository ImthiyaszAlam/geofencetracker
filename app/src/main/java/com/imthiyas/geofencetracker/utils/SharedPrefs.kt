package com.imthiyas.geofencetracker.utils


import android.content.Context

object SharedPrefs {

    private const val PREF_NAME = "geofence_prefs"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Save entry time for a geofence
     */
    fun saveEntryTime(context: Context, geofenceId: String, time: Long) {
        prefs(context)
            .edit()
            .putLong("ENTRY_$geofenceId", time)
            .apply()
    }

    /**
     * Get entry time for a geofence
     */
    fun getEntryTime(context: Context, geofenceId: String): Long {
        return prefs(context)
            .getLong("ENTRY_$geofenceId", 0L)
    }

    /**
     * Clear entry time after exit
     */
    fun clearEntryTime(context: Context, geofenceId: String) {
        prefs(context)
            .edit()
            .remove("ENTRY_$geofenceId")
            .apply()
    }
}

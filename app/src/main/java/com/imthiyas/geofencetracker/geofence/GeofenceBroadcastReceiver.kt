package com.imthiyas.geofencetracker.geofence


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.imthiyas.geofencetracker.data.db.AppDatabase
import com.imthiyas.geofencetracker.data.db.VisitEntity
import com.imthiyas.geofencetracker.utils.NotificationUtils
import com.imthiyas.geofencetracker.utils.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // Safety checks
        if (context == null || intent == null) return

        val event = GeofencingEvent.fromIntent(intent)
        if (event == null || event.hasError()) return


        val transition = event?.geofenceTransition
        val triggeringGeofence = event?.triggeringGeofences?.firstOrNull() ?: return
        val geofenceId = triggeringGeofence.requestId
        val currentTime = System.currentTimeMillis()

        CoroutineScope(Dispatchers.IO).launch {

            when (transition) {

                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    SharedPrefs.saveEntryTime(context, geofenceId, currentTime)
                    NotificationUtils.show(
                        context,
                        "Entered geofence: $geofenceId"
                    )
                }

                Geofence.GEOFENCE_TRANSITION_EXIT -> {

                    val entryTime =
                        SharedPrefs.getEntryTime(context, geofenceId)

                    // Ignore invalid exits
                    if (entryTime == 0L) return@launch

                    val duration = currentTime - entryTime

                    AppDatabase.INSTANCE.dao().insertVisit(
                        VisitEntity(
                            geofenceId = 0, // map later if needed
                            geofenceName = geofenceId,
                            entryTime = entryTime,
                            exitTime = currentTime,
                            duration = duration
                        )
                    )

                    SharedPrefs.clearEntryTime(context, geofenceId)

                    NotificationUtils.show(
                        context,
                        "Exited geofence: $geofenceId"
                    )
                }
            }
        }
    }
}

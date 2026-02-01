package com.imthiyas.geofencetracker.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.imthiyas.geofencetracker.R
import com.imthiyas.geofencetracker.geofence.GeofenceHelper
import java.util.UUID

class MapsActivity : AppCompatActivity(), GoogleMap.OnMapLongClickListener {

    private lateinit var map: GoogleMap
    private lateinit var geofenceHelper: GeofenceHelper

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maps)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        geofenceHelper = GeofenceHelper(this)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            map.setOnMapLongClickListener(this)
            enableUserLocation()
        }
    }

    private fun enableUserLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                map.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableUserLocation()
        }
    }

    /**
     * Long press on map → create geofence
     */
    override fun onMapLongClick(latLng: LatLng) {

        // Permission guard (MANDATORY)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                this,
                "Location permission required to add geofence",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val radius = 50f
        val geofenceId = UUID.randomUUID().toString()

        try {
            // Add geofence safely
            geofenceHelper.addGeofence(geofenceId, latLng, radius)
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(this, "Permission error", Toast.LENGTH_SHORT).show()
            return
        }

        // UI marker
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Geofence")
        )

        // Radius circle
        map.addCircle(
            CircleOptions()
                .center(latLng)
                .radius(radius.toDouble())
                .strokeWidth(2f)
        )
    }

}

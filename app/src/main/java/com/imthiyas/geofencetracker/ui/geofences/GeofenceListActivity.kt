package com.imthiyas.geofencetracker.ui.geofences

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imthiyas.geofencetracker.R
import com.imthiyas.geofencetracker.data.db.AppDatabase
import com.imthiyas.geofencetracker.ui.adapter.GeofenceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeofenceListActivity : AppCompatActivity() {

    private lateinit var adapter: GeofenceAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_geofence_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        adapter = GeofenceAdapter()

        val recycler = findViewById<RecyclerView>(R.id.recyclerGeofence)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val data = AppDatabase.INSTANCE.dao().getAllGeofences()
            withContext(Dispatchers.Main) {
                adapter.submitList(data)
            }
        }
    }
}
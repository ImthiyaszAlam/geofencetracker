package com.imthiyas.geofencetracker.ui.visits

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imthiyas.geofencetracker.R
import com.imthiyas.geofencetracker.data.db.AppDatabase
import com.imthiyas.geofencetracker.ui.adapter.VisitAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitListActivity : AppCompatActivity() {


    private lateinit var adapter: VisitAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_visit_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = VisitAdapter()

        val recycler = findViewById<RecyclerView>(R.id.recyclerVisits)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val visits = AppDatabase.INSTANCE.dao().getAllVisits()
            withContext(Dispatchers.Main) {
                adapter.submitList(visits)
            }
        }
    }
}
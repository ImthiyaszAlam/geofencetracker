package com.imthiyas.geofencetracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imthiyas.geofencetracker.R
import com.imthiyas.geofencetracker.data.db.GeofenceEntity
import com.imthiyas.geofencetracker.data.db.VisitEntity
import java.text.DateFormat
import java.util.Date
class GeofenceAdapter :
    RecyclerView.Adapter<GeofenceAdapter.GeofenceViewHolder>() {

    private val items = mutableListOf<GeofenceEntity>()

    fun submitList(data: List<GeofenceEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class GeofenceViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLatLng: TextView = itemView.findViewById(R.id.tvLatLng)
        val tvRadius: TextView = itemView.findViewById(R.id.tvRadius)
        val tvCreated: TextView = itemView.findViewById(R.id.tvCreated)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GeofenceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_geofence, parent, false)
        return GeofenceViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GeofenceViewHolder,
        position: Int
    ) {
        val geofence = items[position]

        holder.tvName.text = geofence.name
        holder.tvLatLng.text =
            "Lat: ${geofence.lat}, Lng: ${geofence.lng}"
        holder.tvRadius.text =
            "Radius: ${geofence.radius} meters"
        holder.tvCreated.text =
            "Created: ${
                DateFormat.getDateTimeInstance()
                    .format(Date(geofence.createdAt))
            }"
    }

    override fun getItemCount(): Int = items.size
}

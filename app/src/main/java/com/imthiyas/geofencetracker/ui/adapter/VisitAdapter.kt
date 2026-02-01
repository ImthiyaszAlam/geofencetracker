package com.imthiyas.geofencetracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imthiyas.geofencetracker.R
import com.imthiyas.geofencetracker.data.db.VisitEntity
import java.text.DateFormat
import java.util.Date

class VisitAdapter :
    RecyclerView.Adapter<VisitAdapter.VisitViewHolder>() {

    private val items = mutableListOf<VisitEntity>()

    fun submitList(data: List<VisitEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class VisitViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvEntry: TextView = itemView.findViewById(R.id.tvEntry)
        val tvExit: TextView = itemView.findViewById(R.id.tvExit)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VisitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visit, parent, false)
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: VisitViewHolder,
        position: Int
    ) {
        val visit = items[position]

        holder.tvLocation.text = visit.geofenceName

        holder.tvDate.text =
            "Date: ${
                DateFormat.getDateInstance()
                    .format(Date(visit.entryTime))
            }"

        holder.tvEntry.text =
            "Entry: ${
                DateFormat.getTimeInstance()
                    .format(Date(visit.entryTime))
            }"

        holder.tvExit.text =
            "Exit: ${
                DateFormat.getTimeInstance()
                    .format(Date(visit.exitTime))
            }"

        val minutes = visit.duration / 60000
        val seconds = (visit.duration % 60000) / 1000

        holder.tvDuration.text =
            "Duration: ${minutes}m ${seconds}s"
    }

    override fun getItemCount(): Int = items.size
}

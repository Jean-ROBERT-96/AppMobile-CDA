package com.example.funfusion.salle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.funfusion.R
import com.example.funfusion.salle.Salle

class SalleAdapter(private val salles: List<Salle>) : RecyclerView.Adapter<SalleAdapter.SalleViewHolder>() {

    inner class SalleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.adresse)
        private val cityTextView: TextView = itemView.findViewById(R.id.city)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        private val parkingInfoTextView: TextView = itemView.findViewById(R.id.parkingInfo)
        private val transportTextView: TextView = itemView.findViewById(R.id.Transport)

        fun bind(salle: Salle) {
            addressTextView.text = "Adresse: ${salle.address1}, ${salle.address2}"
            cityTextView.text = "Ville: ${salle.city}"
            descriptionTextView.text = "Description: ${salle.description}"
            parkingInfoTextView.text = "Info de stationnement: ${salle.parkingInfo}"
            transportTextView.text = "Transport: ${salle.publicTransport}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_salle, parent, false)
        return SalleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalleViewHolder, position: Int) {
        val salle = salles[position]
        holder.bind(salle)
    }

    override fun getItemCount() = salles.size
}
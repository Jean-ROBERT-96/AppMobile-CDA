package com.example.funfusion.salle

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.funfusion.R

class SalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle)


        val address1 = intent.getStringExtra("address1")
        val address2 = intent.getStringExtra("address2")
        val city = intent.getStringExtra("city")
        val parkingInfo = intent.getStringExtra("parkingInfo")
        val description = intent.getStringExtra("description")
        val publicTransport = intent.getStringExtra("publicTransport")


        val adresseTextView = findViewById<TextView>(R.id.adresse)
        val cityTextView = findViewById<TextView>(R.id.city)
        val descriptionTextView = findViewById<TextView>(R.id.description)
        val parkingInfoTextView = findViewById<TextView>(R.id.parkingInfo)
        val transportTextView = findViewById<TextView>(R.id.Transport)




        adresseTextView.text = "Adresse: $address2, $address1"
        cityTextView.text = "Ville : $city"
        descriptionTextView.text = "Description: $description"
        parkingInfoTextView.text = "Parking info: $parkingInfo"
        transportTextView.text = "$publicTransport"
    }

}
package com.example.funfusion.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.funfusion.R
import com.example.funfusion.salle.Salle
import com.example.funfusion.salle.SalleActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var googleMap: GoogleMap
    private val sallesList = mutableListOf<Salle>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMarkerClickListener(this)

        val okHttpClient = OkHttpClient.Builder().build()
        val requestUrl = "https://ugarit-online.000webhostapp.com/epsi/films/salles.json"
        val request = Request.Builder().url(requestUrl).get().build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Gérer l'échec de la requête ici
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    val jsSalles = JSONObject(data)
                    val jsArraySalles = jsSalles.getJSONArray("salles")

                    activity?.runOnUiThread {
                        // Ajouter un marqueur sur la carte pour chaque salle
                        for (i in 0 until jsArraySalles.length()) {
                            val js = jsArraySalles.getJSONObject(i)
                            val salle = Salle(
                                js.optString("id"),
                                js.optString("name"),
                                js.optString("address1"),
                                js.optString("address2"),
                                js.optString("city"),
                                js.optString("latitude"),
                                js.optString("longitude"),
                                js.optString("parkingInfo"),
                                js.optString("description"),
                                js.optString("publicTransport"),
                            )
                            sallesList.add(salle)

                            // Ajouter un marqueur sur la carte pour chaque salle
                            val salleLatLng = LatLng(
                                js.optDouble("latitude", 0.0),
                                js.optDouble("longitude", 0.0)
                            )
                            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_funfusion)
                            val smallerBitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, false)
                            val markerOptions = MarkerOptions()
                                .position(salleLatLng)
                                .title(js.optString("name"))
                                .icon(BitmapDescriptorFactory.fromBitmap(smallerBitmap))

                            val marker = googleMap.addMarker(markerOptions)

                            // Attribuer l'index de la salle comme tag au marqueur
                            marker?.tag = i
                        }
                    }
                }
            }
        })

        // Centrer la carte sur une position par défaut
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), DEFAULT_ZOOM))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Récupérer les informations sur la salle associée à ce marqueur
        val salleIndex = marker.tag as? Int
        salleIndex?.let { index ->
            val salle = sallesList[index]

            // Lorsque vous cliquez sur un marqueur, lancez SalleActivity avec les informations de la salle
            val intent = Intent(requireContext(), SalleActivity::class.java)
            intent.putExtra("address1", salle.address1)
            intent.putExtra("address2", salle.address2)
            intent.putExtra("city", salle.city)
            intent.putExtra("parkingInfo", salle.parkingInfo)
            intent.putExtra("description", salle.description)
            intent.putExtra("publicTransport", salle.publicTransport)

            startActivity(intent)
            return true
        }
        return false
    }

    companion object {
        private const val DEFAULT_LATITUDE = 48.856614
        private const val DEFAULT_LONGITUDE = 2.352222
        private const val DEFAULT_ZOOM = 10f

        @JvmStatic
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}

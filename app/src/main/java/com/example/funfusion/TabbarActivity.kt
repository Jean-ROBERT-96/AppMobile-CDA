package com.example.funfusion

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.funfusion.map.MapFragment
import com.example.funfusion.movie_list.MovieFragment
import com.example.funfusion.pannier.CartFragment
import com.example.funfusion.qrcode.QRCodeFragment

class TabbarActivity : AppCompatActivity() {

    private val movieFragment = MovieFragment.newInstance()
    private val cartFragment = CartFragment.newInstance()
    private val mapFragment = MapFragment.newInstance() // Utiliser MapFragment à la place de MapActivity
    private val qrcodeFragment = QRCodeFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbar)

        val tab1 = findViewById<TextView>(R.id.textViewTab1)
        val tab2 = findViewById<TextView>(R.id.textViewTab2)
        val tab3 = findViewById<TextView>(R.id.textViewTab3)
        val tab4 = findViewById<TextView>(R.id.textViewTab4)

        showTab1()

        tab1.setOnClickListener {
            showTab1()
        }

        tab2.setOnClickListener {
            showTab2()
        }

        tab3.setOnClickListener {
            showTab3()
        }

        tab4.setOnClickListener {
            showTab4()
        }
    }

    private fun showTab1() {
        val frManager = supportFragmentManager
        val fragmentTra = frManager.beginTransaction()
        fragmentTra.addToBackStack("Tab1")
        fragmentTra.replace(R.id.layoutContent, movieFragment)
        fragmentTra.commit()
    }

    private fun showTab2() {
        val frManager = supportFragmentManager
        val fragmentTra = frManager.beginTransaction()
        fragmentTra.addToBackStack("Tab2")
        fragmentTra.replace(R.id.layoutContent, mapFragment) // Utiliser MapFragment à la place de MapActivity
        fragmentTra.commit()
    }

    private fun showTab3() {
        val frManager = supportFragmentManager
        val fragmentTra = frManager.beginTransaction()
        fragmentTra.addToBackStack("Tab3")
        fragmentTra.replace(R.id.layoutContent, qrcodeFragment)
        fragmentTra.commit()
    }

    private fun showTab4() {
        val frManager = supportFragmentManager
        val fragmentTra = frManager.beginTransaction()
        fragmentTra.addToBackStack("Tab4")
        fragmentTra.replace(R.id.layoutContent, cartFragment)
        fragmentTra.commit()
    }
}

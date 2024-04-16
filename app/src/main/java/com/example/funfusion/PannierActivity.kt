package com.example.funfusion
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funfusion.entities.CartItem
import com.example.funfusion.movie_list.CartManager
import com.example.funfusion.pannier.CartAdapter

class PannierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pannier)

        val cartItems = CartManager.getCartItems()

        setupRecyclerView(cartItems)
    }

    private fun setupRecyclerView(cartItems: List<CartItem>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(cartItems)
        recyclerView.adapter = adapter
    }
}
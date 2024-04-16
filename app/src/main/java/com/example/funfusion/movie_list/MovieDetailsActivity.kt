package com.example.funfusion.movie_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.funfusion.PannierActivity
import com.example.funfusion.R
import com.example.funfusion.TabbarActivity
import com.example.funfusion.entities.CartItem
import com.example.funfusion.entities.Movie
import com.example.funfusion.pannier.CartFragment
import com.google.gson.Gson

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(cartItem: CartItem) {
        cartItems.add(cartItem)
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }
}
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Récupérer l'objet Movie de l'intent
        this.movie = (intent.getSerializableExtra(MovieFragment.NEXT_SCREEN) as? Movie)!!

        // Afficher les détails du film
        val backGraphics = findViewById<ImageView>(R.id.textViewGraphicUrl)
        Glide.with(this).load(movie.backdropUrl).into(backGraphics)

        val viewTime = findViewById<TextView>(R.id.textViewTime)
        viewTime.append(" " + movie.runTime.toString() + " " + "min")
        val viewDesc = findViewById<TextView>(R.id.textViewDescription)
        viewDesc.append(" " + movie.description)

        // Ajouter le film au panier
        val buttonAddCart = findViewById<Button>(R.id.clickCart)
        buttonAddCart.setOnClickListener{
            val cartItem = CartItem(movie)
            CartManager.addToCart(cartItem)
            startActivity(Intent(this, TabbarActivity::class.java))
        }
    }
}
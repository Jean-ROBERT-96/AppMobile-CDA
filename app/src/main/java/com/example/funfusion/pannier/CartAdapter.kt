package com.example.funfusion.pannier
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funfusion.R
import com.example.funfusion.entities.CartItem

class CartAdapter(private var cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // ViewHolder
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val imageViewMovie: ImageView = itemView.findViewById(R.id.imageViewMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.textViewTitle.text = cartItem.movie.title

        Glide.with(holder.itemView)
            .load(cartItem.movie.graphicUrl)
            .into(holder.imageViewMovie)

    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

}
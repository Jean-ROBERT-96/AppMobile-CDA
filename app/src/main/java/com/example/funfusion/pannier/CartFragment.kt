package com.example.funfusion.pannier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funfusion.R
import com.example.funfusion.movie_list.CartManager

class CartFragment : Fragment() {
    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_cart, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val cartItems = CartManager.getCartItems()


        val cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter

        return view
    }
}
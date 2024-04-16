package com.example.funfusion.entities

import java.io.Serializable

class CartItem(
    val movie: Movie,
    val quantity: Int = 1 // Vous pouvez ajouter la quantité si nécessaire
) : Serializable {
}
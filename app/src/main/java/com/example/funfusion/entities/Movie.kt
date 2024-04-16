package com.example.funfusion.entities

import java.io.Serializable

class Movie(
    val id:String,
    val title:String,
    val description:String,
    val runTime:Int,
    val graphicUrl:String,
    val backdropUrl:String): Serializable { }
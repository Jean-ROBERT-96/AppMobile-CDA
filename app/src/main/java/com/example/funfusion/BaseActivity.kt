package com.example.funfusion


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Espi","############### onCreate ###############"+javaClass.simpleName)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Espi","############### onStart ###############"+javaClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        Log.d("Espi","############### onStop ###############"+javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.d("Espi","############### onResume ###############"+javaClass.simpleName)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Espi","############### onRestart ###############"+javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Espi","############### onPause ###############"+javaClass.simpleName)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Espi","############### onDestroy ###############"+javaClass.simpleName)
    }


    fun showBack(){
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.visibility=View.VISIBLE
        imageViewBack.setOnClickListener {
            this.finish()
        }
    }

    fun setHeaderTitle(title:String?){
        if(title!=null) {
            val textViewNature = findViewById<TextView>(R.id.textViewTitle)
            textViewNature.text = title
        }
    }
}
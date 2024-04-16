package com.example.funfusion.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funfusion.R
import com.example.funfusion.entities.Movie

class MovieAdapter(
    private val data: ArrayList<Movie>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<MovieAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewGraphicUrl = view.findViewById<ImageView>(R.id.textViewGraphicUrl)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_movie, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie: Movie = data[position]

        holder.textViewTitle.text=movie.title
        Glide.with(holder.textViewGraphicUrl.context).load(movie.graphicUrl).into(holder.textViewGraphicUrl);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface RecyclerViewEvent{
        fun onItemClick(position: Int)
    }
}
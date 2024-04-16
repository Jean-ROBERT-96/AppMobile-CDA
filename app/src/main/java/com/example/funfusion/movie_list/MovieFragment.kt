package com.example.funfusion.movie_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funfusion.R
import com.example.funfusion.entities.Movie
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
class MovieFragment : Fragment(), MovieAdapter.RecyclerViewEvent {
    private lateinit var data : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_movie, container, false)

        val movies= arrayListOf<Movie>()

        val recyclerViewMovies= view.findViewById<RecyclerView>(R.id.recyclerViewMovies)
        recyclerViewMovies.layoutManager= LinearLayoutManager(context)
        val movieAdapter=MovieAdapter(movies, this)
        recyclerViewMovies.adapter=movieAdapter

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mRequestUrl="https://ugarit-online.000webhostapp.com/epsi/films/movies.json"
        val request= Request.Builder().url(mRequestUrl).get().cacheControl(CacheControl.FORCE_NETWORK).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException){
                Log.e("MovieFragment", "Erreur lors de la requête HTTP: ${e.message}")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    val jsMovies = JSONObject(data)
                    val jsArrayMovies = jsMovies.getJSONArray("movies")
                    for (i in 0 until jsArrayMovies.length()) {
                        val js = jsArrayMovies.getJSONObject(i)
                        val movie = Movie(
                            js.optString("id"),
                            js.optString("title"),
                            js.optString("description"),
                            js.optInt("runTime"),
                            js.optString("graphicUrl"),
                            js.optString("backdropUrl")
                        )
                        movies.add(movie)
                    }
                    activity?.runOnUiThread {
                        movieAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
        this.data = movies

        return view
    }

    companion object{
        val NEXT_SCREEN="details_screen"
        @JvmStatic
        fun newInstance() =
            MovieFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onItemClick(position: Int) {
        val movie = data[position]
        val intent = Intent (activity, MovieDetailsActivity::class.java)
        intent.putExtra(NEXT_SCREEN, movie)
        startActivity(intent)
    }

}
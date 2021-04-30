package com.example.workout.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView : RecyclerView

    private var titles = mutableListOf<String>()
    private var descs = mutableListOf<String>()
    private var images = mutableListOf<String>()
    private var links = mutableListOf<String>()

    private fun requestNews(){
        val retrofitapi = Retrofit.Builder().baseUrl("https://newsapi.org").addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(Request::class.java);
        GlobalScope.launch(Dispatchers.IO) {
            val res = retrofitapi.getResponse();

            for(article in res.articles){
                titles.add(article.title)
                descs.add(article.description)
                images.add(article.urlToImage)
                links.add(article.url)
                Log.d("Tirle", article.title);
            }
            withContext(Dispatchers.Main) {
//                rv_recyclerView.layoutManager = LinearLayoutManager(getActivity()?.getApplicationContext())
                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    recyclerView.layoutManager = GridLayoutManager(
                        getActivity()?.getApplicationContext(),
                        2
                    )
                } else {
                    recyclerView.layoutManager = GridLayoutManager(
                        getActivity()?.getApplicationContext(),
                        1
                    )
                }

                recyclerView.adapter = RecyclerAdapter(titles, descs, images, links)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        this.recyclerView = root.findViewById(R.id.recyclerView);

        Log.d("Debug : ", "Testttttttttttttttttttttttttttttttttttttttt");
        this.requestNews();

        return root
    }
}
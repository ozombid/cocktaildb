package com.example.myapplication3.ui.search

import com.example.myapplication3.ui.ApiService
import SearchAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FragmentSearchBinding
import com.example.myapplication3.ui.recipes.RecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var rv: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var myClassAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // RecyclerView setup
        rv = root.findViewById(R.id.rv_search)

        myClassAdapter = SearchAdapter(requireContext(), emptyArray(), emptyArray(), emptyArray())
        rv.adapter = myClassAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        // SearchView setup
        searchView = root.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchDataFromApi(it)
                }
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    fetchDataFromApi(it)
                }
                return true
            }
        })

        return root
    }

    private fun fetchDataFromApi(recipeName: String) {
        // Initialisation de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)


        api.getRecipeDrink(recipeName).enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    val images = response.body()?.drinks?.mapNotNull { it.strDrinkThumb }
                    val names = response.body()?.drinks?.mapNotNull { it.strDrink }
                    val ids = response.body()?.drinks?.mapNotNull { it.idDrink }

                    images?.let {
                        // Log the data retrieved from the API
                        Log.d("API_DATA", it.toString())

                        // Update adapter data and notify data set changed
                        myClassAdapter.setImagesData(it.toTypedArray())
                    }
                    names?.let {
                        // Log the data retrieved from the API
                        Log.d("API_DATA", it.toString())

                        // Update adapter data and notify data set changed
                        myClassAdapter.setNamesData(it.toTypedArray())
                    }

                    ids?.let {
                        // Update adapter data with IDs and notify data set changed
                        myClassAdapter.setIdsData(it.toTypedArray())
                    }
                } else {
                    // Handle error cases
                    Log.e("API_ERROR", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                // Handle request failures
                Log.e("API_FAILURE", "API request failed: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

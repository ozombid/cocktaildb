package com.example.myapplication3.ui.ingredients

import com.example.myapplication3.ui.ApiService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FragmentIngredientsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rv: RecyclerView
    private lateinit var adapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // RecyclerView setup
        rv = root.findViewById(R.id.rv_ingredients)
        adapter = IngredientsAdapter(requireContext(), emptyArray())
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        // Call API to fetch data
        fetchDataFromApi()
        return root
    }

    private fun fetchDataFromApi() {
        // Initialisation de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.getIngredients().enqueue(object : Callback<IngredientResponse> {
            override fun onResponse(
                call: Call<IngredientResponse>,
                response: Response<IngredientResponse>
            ) {
                if (response.isSuccessful) {
                    val ingredients = response.body()?.drinks?.mapNotNull { it.strIngredient1 }
                    ingredients?.let {
                        // Log the data retrieved from the API
                        Log.d("API_DATA", it.toString())

                        // Update adapter data and notify data set changed
                        adapter.setData(it.toTypedArray())
                    }
                } else {
                    // Handle error cases
                    Log.e("API_ERROR", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<IngredientResponse>, t: Throwable) {
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

package com.example.myapplication3.ui.categories.filter

import com.example.myapplication3.ui.ApiService
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FilterBinding
import com.example.myapplication3.ui.recipes.RecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilterByCategoryActivity : AppCompatActivity() {
    private var _binding: FilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var rv: RecyclerView
    private lateinit var filter: FilterByCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup
        rv = findViewById(R.id.rv_filter)
        val names = arrayOf("Item 1", "Item 2", "Item 3", "Item 1", "Item 2", "Item 3") // Replace with your data
        val images = arrayOf("https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg","https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg","https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg","https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg","https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg","https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg") // Replace with your images
        val ids = arrayOf("1", "2", "3", "4", "5", "6")

        filter = FilterByCategoryAdapter(this, names, images, ids)
        rv.adapter = filter
        rv.layoutManager = LinearLayoutManager(this)

        // Call API to fetch data
        val category = intent.getStringExtra("category")
        if (category != null) {
            fetchDataFromApi(category)
        } else {
            // Case there is no ID
        }
    }

    private fun fetchDataFromApi(category : String) {
        // Initialisation de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)


        api.getDetailsByCategory(category).enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    val images = response.body()?.drinks?.mapNotNull { it.strDrinkThumb }
                    val names = response.body()?.drinks?.mapNotNull { it.strDrink }
                    val ids = response.body()?.drinks?.mapNotNull { it.idDrink } // Extrayez les IDs

                    images?.let {
                        // Log the data retrieved from the API
                        Log.d("API_DATA", it.toString())

                        // Update adapter data and notify data set changed
                        filter.setImagesData(it.toTypedArray())
                    }
                    names?.let {
                        // Log the data retrieved from the API
                        Log.d("API_DATA", it.toString())

                        // Update adapter data and notify data set changed
                        filter.setNamesData(it.toTypedArray())
                    }

                    ids?.let {
                        // Update adapter data with IDs and notify data set changed
                        filter.setIdsData(it.toTypedArray())
                    }

                } else {
                    // Gérez les cas d'erreur
                    Log.e("API_ERROR", "Échec de la récupération des données: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                // Gérez les échecs de requête
                Log.e("API_FAILURE", "La requête API a échoué: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

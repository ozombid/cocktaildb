package com.example.myapplication3.ui.ingredients

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.ui.ingredients.filter.FilterByIngredientActivity

class IngredientsAdapter(
    private val context: Context,
    private var originalNames: Array<String>,
) : RecyclerView.Adapter<IngredientsAdapter.MyViewClass>() {

    private var filteredNames: MutableList<String> = mutableListOf();

    // Initializer block to set up the adapter
    init {
        // Initially, the filtered data is the same as the original data
        filteredNames.addAll(originalNames);
    }

    // Inner class defining the ViewHolder pattern for each item in the RecyclerView
    class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.text_listing);
    }

    // Method to create new ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        // Inflate the layout for each item of the RecyclerView
        val inflater = LayoutInflater.from(parent.context);
        val itemView = inflater.inflate(R.layout.template_listing, parent, false);
        return MyViewClass(itemView);
    }

    // Method to bind data to each ViewHolder
    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        // Set the text for each category name in the RecyclerView
        holder.txtName.text = filteredNames[position].toString();

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FilterByIngredientActivity::class.java)
            intent.putExtra("ingredient", filteredNames[position])
            context.startActivity(intent)
        }
    }

    // Method to get the count of items in the adapter
    override fun getItemCount(): Int {
        // Returns the size of the filtered list
        return filteredNames.size;
    }

    // Method to update the adapter's data
    fun setData(newData: Array<String>) {
        // Update the original data and refresh the filtered data
        originalNames = newData;
        filteredNames.clear();
        filteredNames.addAll(newData);
        // Notify the adapter that the data set has changed so it can update the view
        notifyDataSetChanged();
    }
}

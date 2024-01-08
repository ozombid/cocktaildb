package com.example.myapplication3.ui.categories.filter;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication3.R
import com.example.myapplication3.ui.TestActivity

class FilterByCategoryAdapter(
    private val context: Context,
    private var originalNames: Array<String>,
    private var originalImages: Array<String>,
    private var originalIds: Array<String>
) : RecyclerView.Adapter<FilterByCategoryAdapter.MyViewClass>() {
    private var filteredNames: MutableList<String> = mutableListOf()
    private var filteredImages: MutableList<String> = mutableListOf()
    private var filteredIds: MutableList<String> = mutableListOf()

    init {
        filteredNames.addAll(originalNames)
        filteredImages.addAll(originalImages)
        filteredIds.addAll(originalIds)
    }

    class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.textView3)
        var logo: ImageView = itemView.findViewById(R.id.logoProfil)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.template_search, parent, false)
        return MyViewClass(itemView)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        val currentItemName = filteredNames[position]
        val currentItemImage = filteredImages[position]
        val currentItemId = filteredIds[position]

        holder.txtName.text = currentItemName

        // Charger l'image à partir de l'URL ou du chemin
        Glide.with(context)
            .load(currentItemImage)
            .into(holder.logo)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TestActivity::class.java)
            intent.putExtra("ID", currentItemId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredNames.size
    }

    fun setNamesData(namesData: Array<String>) {
        originalNames = namesData
        filteredNames.clear()
        filteredNames.addAll(namesData)
        notifyDataSetChanged()
    }

    fun setImagesData(imagesData: Array<String>) {
        originalImages = imagesData
        filteredImages.clear()
        filteredImages.addAll(imagesData)
        notifyDataSetChanged()
    }

    fun setIdsData(idsData: Array<String>) {
        originalIds = idsData
        filteredIds.clear()
        filteredIds.addAll(idsData)
        notifyDataSetChanged()
    }
}
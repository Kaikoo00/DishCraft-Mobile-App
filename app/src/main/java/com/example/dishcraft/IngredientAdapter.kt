package com.example.dishcraft

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class IngredientAdapter(private val ingredientList: List<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.ingredient_name)
        val veganStatusTextView: TextView = itemView.findViewById(R.id.ingredient_vegan_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.nameTextView.text = ingredient.name
        holder.veganStatusTextView.text = if (ingredient.isVegan) "Vegan" else "Non-Vegan"
    }

    override fun getItemCount() = ingredientList.size
}

package com.example.dishcraft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class IngredientAdapterCheckbox(private val ingredientList: List<IngredientSelect>) :
    RecyclerView.Adapter<IngredientAdapterCheckbox.IngredientViewHolder>() {

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.ingredient_name)
        val isVeganTextView: TextView = itemView.findViewById(R.id.ingredient_isVegan)
        val checkBox: CheckBox = itemView.findViewById(R.id.ingredient_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkbox, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.nameTextView.text = ingredient.name
        holder.isVeganTextView.text = if (ingredient.isVegan) "Vegan" else "Not Vegan"
        holder.checkBox.isChecked = ingredient.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            ingredient.isSelected = isChecked
       }
    }

    override fun getItemCount() = ingredientList.size
}
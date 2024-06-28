package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException




class CreateRecipe : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientAdapterCheckbox
    private lateinit var ingredientList: MutableList<IngredientSelect>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_recipe)


        Log.d("CreateRecipe", "onCreate called")


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the buttons and set click listeners
        findViewById<Button>(R.id.button_back)?.setOnClickListener {
            val intentback2 = Intent(this, MainActivity::class.java)
            startActivity(intentback2)
        }

        findViewById<Button>(R.id.button_generate)?.setOnClickListener {
            if (isAnyIngredientSelected()) {
                val selectedIngredients = ingredientList.filter { it.isSelected }.map { it.name }
                val intentGenerate = Intent(this, FinalRecipe::class.java)
                intentGenerate.putStringArrayListExtra("selectedIngredients", ArrayList(selectedIngredients))
                startActivity(intentGenerate)
            } else {
                Toast.makeText(this, "Please select at least one ingredient.", Toast.LENGTH_SHORT).show()
            }
        }


//        findViewById<Button>(R.id.button_generate)?.setOnClickListener {
//            if (isAnyIngredientSelected()) {
//                val selectedIngredients = ingredientList.filter { it.isSelected }.map { it.name }
//                val intentGenerate = Intent(this, FinalRecipe::class.java).apply {
//                    putStringArrayListExtra("selectedIngredients", ArrayList(selectedIngredients))
//                }
//                startActivity(intentGenerate)
//            } else {
//                Toast.makeText(this, "Please select at least one ingredient.", Toast.LENGTH_SHORT).show()
//            }
//        }

        findViewById<Button>(R.id.add_here)?.setOnClickListener {
            val intentaddhere = Intent(this, UploadIngredient::class.java)
           startActivity(intentaddhere)
        }

        recyclerView = findViewById(R.id.recyclerview_ingredient_pick)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Read ingredients from the JSON file
        ingredientList = readIngredientsFromJsonFile()

        // Initialize the adapter with the ingredient list
        ingredientAdapter = IngredientAdapterCheckbox(ingredientList)
        recyclerView.adapter = ingredientAdapter
    }

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun isAnyIngredientSelected(): Boolean {
        return ingredientList.any { it.isSelected }
    }

    private fun readIngredientsFromJsonFile(): MutableList<IngredientSelect> {
        val ingredientList = mutableListOf<IngredientSelect>()
        try {
            val file = File(filesDir, "ingredients.json")
            if (file.exists()) {
                val content = file.readText()
                val gson = Gson()
                val type = object : TypeToken<List<Ingredient>>() {}.type
                val ingredients = gson.fromJson<List<Ingredient>>(content, type)
                ingredientList.addAll(ingredients.map { IngredientSelect(it.name, it.isVegan, false) })
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ingredientList
    }
}
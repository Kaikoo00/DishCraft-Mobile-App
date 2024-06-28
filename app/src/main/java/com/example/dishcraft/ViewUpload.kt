package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.widget.ListView
import java.io.IOException
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dishcraft.ui.theme.ItemSpacingDecoration
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class ViewUpload : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_upload)

        val recyclerViewSample = findViewById<RecyclerView>(R.id.recyclerViewIngredients)
        recyclerViewSample.layoutManager = LinearLayoutManager(this)

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


        recyclerView = findViewById(R.id.recyclerViewIngredients)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample list of ingredient names
        val ingredients = readIngredientsFromJsonFile()
//            listOf(
//            "Ayam",
//            "Telur",
//            "Tepung Terigu",
//            "Tepung Roti",
//            "Sambal"
//            // Add more ingredient names as needed
//        )

        ingredientAdapter = IngredientAdapter(ingredients)
        recyclerView.adapter = ingredientAdapter

//
        // Add custom item decoration with desired spacing (e.g., 8dp)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        recyclerView.addItemDecoration(ItemSpacingDecoration(spacingInPixels))


    }

//  From OpenFile
//    private fun readIngredientsFromJsonFile(): List<Ingredient> {
//        val ingredientList = mutableListOf<Ingredient>()
//        try {
//            val fileInputStream = openFileInput("ingredients.json")
//            val inputStreamReader = InputStreamReader(fileInputStream, Charset.forName("UTF-8"))
//            val bufferedReader = BufferedReader(inputStreamReader)
//            val jsonString = bufferedReader.use { it.readText() }
//
//            val gson = Gson()
//            val type = object : TypeToken<List<Ingredient>>() {}.type
//            ingredientList.addAll(gson.fromJson(jsonString, type))
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return ingredientList
//    }


    //from assets
    private fun readIngredientsFromJsonFile(): List<Ingredient> {
        val ingredientList = mutableListOf<Ingredient>()
        try {
            val file = File(filesDir, "ingredients.json")
            if (file.exists()) {
                val content = file.readText()
                val gson = Gson()
                val type = object : TypeToken<List<Ingredient>>() {}.type
                ingredientList.addAll(gson.fromJson(content, type))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ingredientList
    }
}

//






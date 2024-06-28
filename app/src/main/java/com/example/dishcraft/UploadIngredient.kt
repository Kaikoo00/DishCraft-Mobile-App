//package com.example.dishcraft
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import android.content.Context
//import org.json.JSONArray
//import org.json.JSONObject
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.io.IOException
//import com.google.ai.client.generativeai.GenerativeModel //geminiAPI
//import kotlinx.coroutines.runBlocking
//import android.widget.CheckBox
//import android.widget.EditText
//import android.widget.Toast
//
//
//data class Ingredient(val name: String, val isVegan: Boolean)
//data class ChatMessage(val role: String, val content: String)
//
//class UploadIngredient : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_upload_ingredient)
//
//        val editTextName = findViewById<EditText>(R.id.ingredientnameinput)
//        val checkBoxVegan = findViewById<CheckBox>(R.id.vegetarianstatus)
//        val saveButton = findViewById<Button>(R.id.button_upload)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Find the buttons and set click listeners
//        findViewById<Button>(R.id.button_upload)?.setOnClickListener {
//            val intentupload = Intent(this, MainActivity::class.java)
//            startActivity(intentupload)
//
//        }
//
//
//        findViewById<Button>(R.id.button_back)?.setOnClickListener {
//            val name = editTextName.text.toString()
//            val isVegan = checkBoxVegan.isChecked
//
//            if (name.isNotEmpty()) {
//                val ingredient = Ingredient(name, isVegan)
//                writeOrAppendJsonFile(this, "ingredients.json", ingredient)
//                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//    }
//
//    private fun writeOrAppendJsonFile(context: Context, fileName: String, ingredient: Ingredient) {
//        val file = File(context.filesDir, fileName)
//        val jsonArray: JSONArray
//
//        if (file.exists()) {
//            // Read existing file content
//            val content = file.readText()
//            jsonArray = if (content.isNotEmpty()) JSONArray(content) else JSONArray()
//        } else {
//            // Create new JSON array
//            jsonArray = JSONArray()
//        }
//
//        // Create a new JSON object for the ingredient
//        val ingredientJson = JSONObject().apply {
//            put("name", ingredient.name)
//            put("isVegan", ingredient.isVegan)
//        }
//
//        // Add the new ingredient to the JSON array
//        jsonArray.put(ingredientJson)
//
//        // Write the updated JSON array back to the file
//        try {
//            FileOutputStream(file).use { outputStream ->
//                outputStream.write(jsonArray.toString(4).toByteArray())
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(context, "Failed to save ingredient", Toast.LENGTH_SHORT).show()
//        }
//
//
//    }
//}
//


package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import com.google.ai.client.generativeai.GenerativeModel // geminiAPI
import kotlinx.coroutines.runBlocking
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

data class Ingredient(val name: String, val isVegan: Boolean)
data class ChatMessage(val role: String, val content: String)

class UploadIngredient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload_ingredient)

        val editTextName = findViewById<EditText>(R.id.ingredientnameinput)
        val checkBoxVegan = findViewById<CheckBox>(R.id.vegetarianstatus)
        val saveButton = findViewById<Button>(R.id.button_upload)
        val backButton = findViewById<Button>(R.id.button_back)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listener for the upload button
        backButton.setOnClickListener {
            val intentupload = Intent(this, MainActivity::class.java)
            startActivity(intentupload)
        }

        // Set click listener for the back button
        saveButton.setOnClickListener {
            val name = editTextName.text.toString()
            val isVegan = checkBoxVegan.isChecked

            if (name.isNotEmpty()) {
                val ingredient = Ingredient(name, isVegan)
                writeOrAppendJsonFile(this, "ingredients.json", ingredient)
                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeOrAppendJsonFile(context: Context, fileName: String, ingredient: Ingredient) {
        val file = File(context.filesDir, fileName)
        val jsonArray: JSONArray

        if (file.exists()) {
            // Read existing file content
            val content = file.readText()
            jsonArray = if (content.isNotEmpty()) JSONArray(content) else JSONArray()
        } else {
            // Create new JSON array
            jsonArray = JSONArray()
        }

        // Create a new JSON object for the ingredient
        val ingredientJson = JSONObject().apply {
            put("name", ingredient.name)
            put("isVegan", ingredient.isVegan)
        }

        // Add the new ingredient to the JSON array
        jsonArray.put(ingredientJson)

        // Write the updated JSON array back to the file
        try {
            FileOutputStream(file).use { outputStream ->
                outputStream.write(jsonArray.toString(4).toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save ingredient", Toast.LENGTH_SHORT).show()
        }
    }
}
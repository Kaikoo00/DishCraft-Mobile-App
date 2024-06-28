package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.Part
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class Response(val text: String?, val candidates: List<Candidate>)

data class Candidate(val parts: List<Part>)

data class Part(val content: String)



class FinalRecipe : AppCompatActivity() {
    private lateinit var recipeTextView: TextView

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final_recipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button_back)?.setOnClickListener {
            val intentback2 = Intent(this, CreateRecipe::class.java)
            startActivity(intentback2)
        }

//        val selectedIngredients =
//            intent.getStringArrayListExtra("selectedIngredients") ?: arrayListOf()
//        val ingredientsString = selectedIngredients.joinToString(", ")
//
//        val recipeTextView = findViewById<TextView>(R.id.FinalRecipeGuide)
//        //val recipeText = intent.getStringExtra("RECIPE_TEXT")
//        if (selectedIngredients.isNotEmpty()) {
//            generateRecipe(ingredientsString) { recipe ->
//                recipeTextView.text = recipe
//
//            }

        recipeTextView = findViewById(R.id.FinalRecipeGuide)
        val selectedIngredients = intent.getStringArrayListExtra("selectedIngredients") ?: arrayListOf()
        if (selectedIngredients.isNotEmpty()) {
            generateRecipe(selectedIngredients.joinToString(", "))
        } else {
            recipeTextView.text = "No ingredients selected."
        }
        }


//    private fun extractRecipe(response: GenerateContentResponse): String? {
//        // Handle different parts of the response to retrieve the recipe
//        return response.text ?: response.candidates.firstOrNull()?.let { candidate ->
//            candidate.parts.firstOrNull()?.content
//        }
//    }

    private fun extractRecipe(response: GenerateContentResponse): String {
        return response?.text ?: "No recipe generated."
    }

//    private fun extractRecipe(response: Response?): String {
//        return response?.let { resp ->
//            resp.text ?: resp.candidates.firstOrNull()?.text ?: "No recipe generated."
//        } ?: "No recipe generated."
//    }

//    private fun extractRecipe(response: Response?): String {
//        return response?.let { resp ->
//            // Example extraction logic (adjust based on actual GeminiResponse class structure)
//            resp.text ?: resp.candidates.firstOrNull()?.text  ?: "No recipe generated."
//        } ?: "No recipe generated."
//    }



//    private fun enableEdgeToEdge() {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//    }
    private fun generateRecipe(ingredients: String) {
        recipeTextView.text = "Generating recipe..." // Show loading state
        val apiKey = getString(R.string.gemini_api_key)
        val model = GenerativeModel(
            "gemini-1.5-flash",
            apiKey,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 64
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "text/plain"
            }
        )

//        val chatHistory = listOf(
//            // Add any prior context if needed
//        )

//        val chatHistory = listOf(
//            Content("system", "You are a helpful assistant."),
//            Content("user", "Create a recipe using the following ingredients: $ingredients")
//        )

//        val chatHistory = listOf(
//            Part("system", "You are a helpful assistant."),
//           Part("user", "Create a recipe using the following ingredients: $ingredients")
//        )

//        val chatHistory = listOf(
//            Message(content = Content(role = "system", content = "You are a helpful assistant.")),
//            Message(content = Content(role = "user", content = "Create a recipe using the following ingredients: $ingredients"))
//        )

        val chatHistory = listOf(
            "You are a helpful assistant.",
            "Create a recipe using the following ingredients: $ingredients"
        )

       //val chat = model.startChat(chatHistory)



//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val chat = model.startChat()
//                val response = chat.sendMessage("Create a recipe using the following ingredients: $ingredients")
//
//                // Extract the recipe from the response
//                val recipe = extractRecipe(response)
//
//                withContext(Dispatchers.Main) {
//                    callback(recipe ?: "No recipe generated.")
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    callback("Failed to generate recipe: ${e.message}")
//                }
//            }
//        }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val chat = model.startChat() // Optional: Start a chat for potential future context
            val response = chat.sendMessage("Create a recipe using the following ingredients: $ingredients")
            val recipe = extractRecipe(response)
            withContext(Dispatchers.Main) {
                recipeTextView.text = recipe
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                recipeTextView.text = "Failed to generate recipe: ${e.message}"
                Toast.makeText(this@FinalRecipe,"Failed to Create Recipe", Toast.LENGTH_SHORT).show()
            }
        }


    }
}





}
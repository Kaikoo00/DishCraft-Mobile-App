package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<Button>(R.id.button_reset)

        resetButton.setOnClickListener {
            val file = File(filesDir, "ingredients.json")
            try {
                FileOutputStream(file).use { outputStream ->
                    outputStream.write("[]".toByteArray())
                }
                Toast.makeText(this, "Ingredients reset", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to reset ingredients", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the buttons and set click listeners
        findViewById<Button>(R.id.button_create)?.setOnClickListener {
            val intentCreateRecipe = Intent(this, CreateRecipe::class.java)
            startActivity(intentCreateRecipe)
        }

        findViewById<Button>(R.id.button_add)?.setOnClickListener {
            val intentUploadIngredients = Intent(this, UploadIngredient::class.java)
            startActivity(intentUploadIngredients)
        }

        findViewById<Button>(R.id.button_view)?.setOnClickListener {
            val intentViewUpload = Intent(this, ViewUpload::class.java)
            startActivity(intentViewUpload)
        }
    }
}
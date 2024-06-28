package com.example.dishcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class RecipeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button_back)?.setOnClickListener {
            val intentback2 = Intent(this, CreateRecipe::class.java)
            startActivity(intentback2)
        }




        findViewById<Button>(R.id.buttonoption1)?.setOnClickListener {
            val intentbuttonoption1 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption1)
        }
        findViewById<Button>(R.id.buttonoption2)?.setOnClickListener {
            val intentbuttonoption2 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption2)
        }
        findViewById<Button>(R.id.buttonoption3)?.setOnClickListener {
            val intentbuttonoption3 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption3)
        }
        findViewById<Button>(R.id.buttonoption4)?.setOnClickListener {
            val intentbuttonoption4 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption4)
        }
        findViewById<Button>(R.id.buttonoption5)?.setOnClickListener {
            val intentbuttonoption5 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption5)
        }
        findViewById<Button>(R.id.buttonoption6)?.setOnClickListener {
            val intentbuttonoption6 = Intent(this, FinalRecipe::class.java)
            startActivity(intentbuttonoption6)
        }


    }
}
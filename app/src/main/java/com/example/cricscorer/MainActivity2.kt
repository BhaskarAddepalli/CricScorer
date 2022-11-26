package com.example.cricscorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cricscorer.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityMain2Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.textView4.setOnClickListener { view ->
            val i = Intent(applicationContext, MainActivity4::class.java)
            startActivity(i)
        }
        binding.textView.setOnClickListener { view ->
            val i = Intent(applicationContext, LiveMatches::class.java)
            startActivity(i)
        }

        binding.textView2.setOnClickListener { view ->
            val i = Intent(applicationContext, PastMatches::class.java)
            startActivity(i)
        }

    }
}
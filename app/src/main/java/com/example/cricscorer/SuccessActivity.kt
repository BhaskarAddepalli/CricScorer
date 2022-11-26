package com.example.cricscorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cricscorer.databinding.ActivitySuccessBinding
import com.example.cricscorer.ui.home.HomeFragment

class SuccessActivity : AppCompatActivity() {
    lateinit var binding:ActivitySuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySuccessBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.team=intent.getStringExtra("winning_team").toString()
//        Log.d("saidf",intent.getStringExtra("winning_team").toString())
        var i= Intent(this,MainActivity2::class.java)
        binding.home.setOnClickListener{
            startActivity(i)
        }

    }
}
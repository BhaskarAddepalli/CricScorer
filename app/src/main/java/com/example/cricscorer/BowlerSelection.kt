package com.example.cricscorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cricscorer.databinding.ActivityBowlerSelectionBinding

class BowlerSelection : AppCompatActivity() {
    lateinit var binding:ActivityBowlerSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bowler_selection)
        binding=ActivityBowlerSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button8.setOnClickListener{
            Log.d("sai","back to mainactivity5")
            val i= Intent()
            i.putExtra("bowler_name",binding.editTextTextPersonName2.text.toString())
            setResult(18,i)

            finish()

        }

    }
}
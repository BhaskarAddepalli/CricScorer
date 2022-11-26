package com.example.cricscorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cricscorer.databinding.ActivityBatterSelectionBinding
import com.example.cricscorer.databinding.ActivityBowlerSelectionBinding

class BatterSelection : AppCompatActivity() {
    lateinit var binding: ActivityBatterSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bowler_selection)
        binding= ActivityBatterSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button8.setOnClickListener{
            Log.d("sai","back to mainactivity5")
            val i= Intent()
            i.putExtra("batsman_name",binding.editTextTextPersonName2.text.toString())
            i.putExtra("out_batsman_name",binding.editTextTextPersonName3.text.toString())
            setResult(19,i)

            finish()

        }

    }
}
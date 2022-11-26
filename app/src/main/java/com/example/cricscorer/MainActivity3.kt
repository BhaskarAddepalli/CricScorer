package com.example.cricscorer

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricscorer.databinding.ActivityMain3Binding


class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startMatch.setOnClickListener{
        val i = Intent(applicationContext, MainActivity5::class.java)

            val bt1 = binding.PlayerName.text.toString()
            val bt2=binding.Playername1.text.toString()
            val bw=binding.bwname.text.toString()


            if(bt1 =="" || bt2=="" || bw==""){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_LONG).show()
            }
            else {

                val bundle = intent.extras
                if (bundle != null) {
                    i.putExtras(bundle)
                }
//            Log.d("saddk",intent.getStringExtra("overs").toString())
                i.putExtra("bt1", bt1)
                i.putExtra("bt2", bt2)
                i.putExtra("bw", bw)

                startActivity(i)
            }



        }

    }
}
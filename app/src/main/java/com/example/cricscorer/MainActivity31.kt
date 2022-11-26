package com.example.cricscorer

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricscorer.databinding.ActivityMain31Binding


class MainActivity31 : AppCompatActivity() {
    private lateinit var binding: ActivityMain31Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain31Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startMatch.setOnClickListener{
//            val i = Intent(applicationContext, MainActivity5::class.java)

            val i=Intent()
            val bt1 = binding.PlayerName.text.toString()
            val bt2=binding.Playername1.text.toString()
            val bw=binding.bwname.text.toString()
            if(bt1 =="" || bt2=="" || bw==""){
                Toast.makeText(this,"Please fill all details", Toast.LENGTH_LONG).show()
            }
            else {
                i.putExtra("btk1", bt1)
                i.putExtra("btk2", bt2)
                i.putExtra("bwk1", bw)
//            val bundle = intent.extras
//            if (bundle != null) {
//                i.putExtras(bundle)
//            }
                setResult(20, i)

                finish()
            }
        }

    }
}
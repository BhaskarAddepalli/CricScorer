package com.example.cricscorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cricscorer.Chat.ChatAct
import com.example.cricscorer.databinding.ActivityMainActivityneg1Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivityneg1 : AppCompatActivity() {
    private var binding:ActivityMainActivityneg1Binding?=null
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth=FirebaseAuth.getInstance()
        binding =ActivityMainActivityneg1Binding.inflate(layoutInflater)
        val view= binding!!.root
        setContentView(view)

        binding!!.btnSignUp.setOnClickListener{
            val intent= Intent(this,MainActivity0::class.java)
            startActivity(intent)
        }

        binding!!.btnLogin.setOnClickListener{
            val email=binding!!.loginEmail.text.toString()
            val pass=binding!!.loginPassword.text.toString()
            login(email,pass)
        }




    }

    private fun login(email:String,pass:String){


        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if(it.isSuccessful){
               val intent=Intent(this@MainActivityneg1,MainActivity2::class.java)
                finish()
               startActivity(intent)
            }
            else{
                Toast.makeText(this@MainActivityneg1,"User doesnt exist",Toast.LENGTH_LONG).show()
//                val intent=Intent(this@MainActivityneg1,MainActivity2::class.java)
//                finish()
//                startActivity(intent)
            }
        }
    }

}

package com.example.cricscorer

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.example.cricscorer.databinding.ActivityMain0Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity0 : AppCompatActivity() {
    lateinit var binding:ActivityMain0Binding
    lateinit var mauth:FirebaseAuth
    lateinit var mDbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain0Binding.inflate(layoutInflater)
        mauth= FirebaseAuth.getInstance()

        setContentView(binding.root)

        binding!!.btnSignUp.setOnClickListener{
            val name=binding.edtName.text.toString()
            val email=binding.loginEmail.text.toString()
            val pass=binding.loginPassword.text.toString()
            signUp(name,email,pass)
        }

    }

    private fun signUp(name:String,email:String,pass:String){
        mauth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task->

            if(task.isSuccessful){
                addUsertoDatabase(name,email,mauth.currentUser?.uid!!)
                val intent= Intent(this@MainActivity0,MainActivity2::class.java)
                finish()
                startActivity(intent)
            }
            else{
                Toast.makeText(this@MainActivity0,"some error ",Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun addUsertoDatabase(name:String,email:String,uid:String){
        mDbref=FirebaseDatabase.getInstance().getReference()
        mDbref.child("Users").child(uid).setValue(User(name,email,uid))
    }


}
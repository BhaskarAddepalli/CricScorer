package com.example.cricscorer.Chat

import Message
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.R
import com.example.cricscorer.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatAct : AppCompatActivity() {


    lateinit var mssgRec:RecyclerView
    lateinit var mssg:EditText
    lateinit var sendBt:ImageView
    lateinit var  mssgAdapter: MessageAdapter
    lateinit var mssglst:ArrayList<Message>
    lateinit var  mdbref:DatabaseReference

    lateinit var binding:ActivityChatBinding
    val receBx:String?=null
    var sendBx:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Currname=intent.getStringExtra("CurrName")?:"sai"
        mdbref=FirebaseDatabase.getInstance().getReference()
        mssgRec=findViewById(R.id.rcy)
        mssg=findViewById(R.id.edt)
        sendBt=findViewById(R.id.btn)
        mssglst= ArrayList()
        mssgAdapter= MessageAdapter(this,mssglst)
        binding.rcy.layoutManager=LinearLayoutManager(this)
        binding.rcy.adapter=mssgAdapter
        mssgRec.layoutManager=LinearLayoutManager(this)


        Log.d("sdfg","not coming")
        mdbref.child("chats").child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    mssglst.clear()

                    for (snap in snapshot.children){
//                        Log.d("sai")
                        val mssgr=snap.getValue(Message::class.java)
                        mssglst.add(mssgr!!)
                    }
                    mssgAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })

        val Curruid= FirebaseAuth.getInstance().currentUser?.uid
        binding.btn.setOnClickListener{
            val mssge=mssg.text.toString()
            val mssgObj=Message(mssge,Curruid,Currname)
            mdbref.child("chats").child("messages")
                .push().setValue(mssgObj)
            mssg.setText("")
        }



    }
}
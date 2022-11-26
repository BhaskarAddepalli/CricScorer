package com.example.cricscorer.ui

import Message
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.Chat.MessageAdapter
import com.example.cricscorer.R
import com.example.cricscorer.databinding.ActivityChat2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class ChatActivity2 : AppCompatActivity() {
    lateinit var mssgRec: RecyclerView
    lateinit var mssg: EditText
    lateinit var sendBt: ImageView
    lateinit var  mssgAdapter: MessageAdapter
    lateinit var mssglst:ArrayList<Message>
    lateinit var  mdbref: DatabaseReference

    lateinit var binding: ActivityChat2Binding
    val receBx:String?=null
    var sendBx:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChat2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var Currname=intent.getStringExtra("CurrName")?:"sai"
        mdbref= FirebaseDatabase.getInstance().getReference()
        mssgRec=findViewById(R.id.rcy)
        mssg=findViewById(R.id.edt)
        sendBt=findViewById(R.id.btn)
        mssglst= ArrayList()
        mssgAdapter= MessageAdapter(this,mssglst)
        var lm=LinearLayoutManager(this)
        binding.rcy.layoutManager=lm
//        binding.rcy.getLayoutManager()?.scrollToPosition(mssglst.size-1);
        binding.rcy.adapter=mssgAdapter
//        binding.rcy.getLayoutManager()?.scrollToPosition(3);
//        mssgRec.layoutManager= LinearLayoutManager(this)

        Log.d("sdfg","not coming")
        mdbref.child("chats").child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    mssglst.clear()

                    for (snap in snapshot.children){
//                        Log.d("sai")
                        val mssgr=snap.getValue(Message::class.java)
                        mssglst.add(mssgr!!)
                    }
//                    Collections.reverse(mssglst)
                    mssglst.takeLast(30).reversed()
                    mssgAdapter.notifyDataSetChanged()
//                    binding.rcy.getLayoutManager()?.scrollToPosition(3);

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

//        val Curruid= FirebaseAuth.getInstance().currentUser?.uid
//        binding.btn.setOnClickListener{
//            val mssge=mssg.text.toString()
//            val mssgObj=Message(mssge,Curruid,Currname)
//            mdbref.child("chats").child("messages")
//                .push().setValue(mssgObj)
//            mssg.setText("")
//        }

    }

//    private fun scrollToBottom(recyclerView: RecyclerView) {
//        // scroll to last item to get the view of last item
//        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
//        val adapter = recyclerView.adapter
//        val lastItemPosition = adapter!!.itemCount - 1
//        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
//        recyclerView.post { // then scroll to specific offset
//            val target: View? = layoutManager.findViewByPosition(lastItemPosition)
//            if (target != null) {
//                val offset: Int = recyclerView.measuredHeight - target.getMeasuredHeight()
//                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
//            }
//        }
//    }

    fun click(){
        var Currname=intent.getStringExtra("CurrName")?:"sai"
        val Curruid= FirebaseAuth.getInstance().currentUser?.uid
        val mssge=mssg.text.toString()
        val mssgObj=Message(mssge,Curruid,Currname)
        mdbref.child("chats").child("messages")
            .push().setValue(mssgObj)
        mssg.setText("")
    }
}
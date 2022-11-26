package com.example.cricscorer

import Message
import MyScoreListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.Chat.MessageAdapter
import com.example.cricscorer.databinding.ActivityChat2Binding
import com.example.cricscorer.databinding.ActivityScoreCardBinding
import com.example.cricscorer.ui.Batsman
import com.example.cricscorer.ui.Bowler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ScoreCard : AppCompatActivity() {
    lateinit var binding:ActivityScoreCardBinding
    lateinit var databaseref:DatabaseReference
    lateinit var recyclerViewbt: RecyclerView
    lateinit var recyclerViewbw: RecyclerView
    lateinit var recyclerViewbt2nd: RecyclerView
    lateinit var recyclerViewbw2nd: RecyclerView
    lateinit var list:ArrayList<Batsman>
    lateinit var list1:ArrayList<Batsman>
    lateinit var list3:ArrayList<Batsman>
    lateinit var list4:ArrayList<Batsman>
    lateinit var strk:String


    lateinit var mssgRec: RecyclerView
    lateinit var mssg: EditText
    lateinit var sendBt: ImageView
    lateinit var  mssgAdapter: MessageAdapter
    lateinit var mssglst:ArrayList<Message>
    lateinit var  mdbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityScoreCardBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.user= Batsman("Batsman","R","B","4s","6s","SR")
        binding.user1= Bowler("Bowler","O","M","R","W","ER")
        strk= intent.getStringExtra("matchDetails").toString()

        recyclerViewbt=binding.btscorecardrecycle
        recyclerViewbw=binding.bwscorecardrecycle
        recyclerViewbt2nd=binding.btscorecardrecycle1
        recyclerViewbw2nd=binding.bwscorecardrecycle1

        recyclerViewbw.layoutManager=LinearLayoutManager(this)
        recyclerViewbw.setHasFixedSize(true)

        recyclerViewbt.layoutManager=LinearLayoutManager(this)
        recyclerViewbt.setHasFixedSize(true)

        recyclerViewbw2nd.layoutManager=LinearLayoutManager(this)
        recyclerViewbw2nd.setHasFixedSize(true)

        recyclerViewbt2nd.layoutManager=LinearLayoutManager(this)
        recyclerViewbt2nd.setHasFixedSize(true)

        list= arrayListOf<Batsman>()
        list1= arrayListOf<Batsman>()
        list3= arrayListOf<Batsman>()
        list4= arrayListOf<Batsman>()

        getListData()

//        if(list3.size>0|| list4.size>0){
//
//        }



        var Currname=intent.getStringExtra("CurrName")?:"sai"
        mdbref= FirebaseDatabase.getInstance().getReference()
        mssgRec=binding.sc.rcy
        mssg=binding.sc.edt
        sendBt=binding.sc.btn
        mssglst= ArrayList()
        mssgAdapter= MessageAdapter(this,mssglst)
        binding.sc.rcy.layoutManager= LinearLayoutManager(this)
        binding.sc.rcy.adapter=mssgAdapter
        mssgRec.layoutManager= LinearLayoutManager(this)

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
//                    mssglst.takeLast(30).reversed()
                    mssgAdapter.notifyDataSetChanged()
                    binding.sc.rcy.getLayoutManager()?.scrollToPosition(mssglst.size-1);

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        sendBt.setOnClickListener{
            var Currname=intent.getStringExtra("CurrName")?:"sai"
            val Curruid= FirebaseAuth.getInstance().currentUser?.uid
            val mssge=mssg.text.toString()
            val mssgObj=Message(mssge,Curruid,Currname)
            mdbref.child("chats").child("messages")
                .push().setValue(mssgObj)
            mssg.setText("")
        }

    }



    private fun getListData(){
        databaseref=FirebaseDatabase.getInstance().getReference("live_matches").child(strk)

        databaseref.child("1st inning").child("batsman").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

//                list=arrayListOf<Batsman>()
                if(snapshot.exists()){

                    for(it in snapshot.children){
//                        val Btk=it.getValue(Batsman::class.java)

                        val match:HashMap<Any,Any> = it.getValue() as HashMap<Any, Any>

                        Log.d("typedr", match!!.toString())

                    if (match != null) {
//                            (var name:String,var Runs:String, var Balls:String, var fours:String, var sixes:String, var sr:String)
                        list.add(Batsman(match["name"] as String, match["runs"] as String,
                            match["balls"] as String,match["fours"] as String,match["sixes"] as String,match["sr"] as String,
                        ))
                    }
                    }

                    recyclerViewbt.adapter=MyScoreListAdapter(list)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        databaseref.child("1st inning").child("bowler").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
//                list=arrayListOf<Batsman>()
                if(snapshot.exists()){
                    for(it in snapshot.children){
//                        val Btk=it.getValue(Batsman::class.java)

                        val match:HashMap<Any,Any> = it.getValue() as HashMap<Any, Any>

                        Log.d("typedr", match!!.toString())

                        if (match != null) {
//                            (var name:String,var Runs:String, var Balls:String, var fours:String, var sixes:String, var sr:String)
                            list1.add(Batsman(match["name"] as String, match["overs"] as String,
                                match["maidens"] as String,match["runs"] as String,match["wickets"] as String,match["er"] as String,
                            ))
                        }
                        recyclerViewbw.adapter=MyScoreListAdapter(list1)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        databaseref.child("2nd inning").child("batsman").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

//                list=arrayListOf<Batsman>()
                if(snapshot.exists()){

                    for(it in snapshot.children){
//                        val Btk=it.getValue(Batsman::class.java)

                        val match:HashMap<Any,Any> = it.getValue() as HashMap<Any, Any>

                        Log.d("typedr", match!!.toString())

                        if (match != null && match["sr"] != null) {
//                            (var name:String,var Runs:String, var Balls:String, var fours:String, var sixes:String, var sr:String)

                            list3.add(Batsman(match["name"] as String, match["runs"] as String,
                                match["balls"] as String,match["fours"] as String,match["sixes"] as String,match["sr"] as String,
                            ))
                        }
                    }
                    recyclerViewbt2nd.adapter=MyScoreListAdapter(list3)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        databaseref.child("2nd inning").child("bowler").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
//                list=arrayListOf<Batsman>()
                if(snapshot.exists()){
                    for(it in snapshot.children){
//                        val Btk=it.getValue(Batsman::class.java)

                        val match:HashMap<Any,Any> = it.getValue() as HashMap<Any, Any>

                        Log.d("typedr", match!!.toString())

                        if (match != null) {
//                            (var name:String,var Runs:String, var Balls:String, var fours:String, var sixes:String, var sr:String)
                            list4.add(Batsman(match["name"] as String, match["overs"] as String,
                                match["maidens"] as String,match["runs"] as String,match["wickets"] as String,match["er"] as String,
                            ))
                        }
                        recyclerViewbw2nd.adapter=MyScoreListAdapter(list4)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
}









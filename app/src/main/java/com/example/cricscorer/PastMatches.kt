package com.example.cricscorer

import LIveMatchesAdapter
import Match
import SelectListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricscorer.databinding.ActivityPastMatchesBinding
import com.example.cricscorer.ui.Batsman
import com.google.firebase.database.*
import kotlinx.coroutines.runBlocking

class PastMatches : AppCompatActivity(),SelectListener {
    lateinit var binding:ActivityPastMatchesBinding
    lateinit var list:ArrayList<Match>
    lateinit var databaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_live_matches)

        binding= ActivityPastMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.liveMatchesrecy.layoutManager=LinearLayoutManager(this)
        binding.liveMatchesrecy.setHasFixedSize(true)
        list= arrayListOf<Match>()
//        runBlocking {
            getListData()
//        }
        Log.d("sedr",list.size.toString())

    }


    private fun getListData() {
        databaseref = FirebaseDatabase.getInstance().getReference("past_matches")
        var databaseref2=FirebaseDatabase.getInstance().getReference("live_matches")
        databaseref.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    list= arrayListOf<Match>()
                    for (it in snapshot.children) {
                        val x2=it.getValue(String::class.java)
                        Log.d("sedr1",x2.toString())
                         var x=databaseref2.child(x2.toString())
                        Log.d("sedr2",x.toString())
                          var x3= x.child("Match_details").get()
                        Log.d("sedr",x3.toString())
                        x3.addOnSuccessListener {match ->
                            Log.d("sedr3",match.toString())
//                            Log.d("sedr1",match.child("id").value.toString())
//                            match=match["Match_details"]
                            if(match.exists()){
                                Log.d("sedr1",match.child("id").value.toString())
                                list.add(Match(match.child("id").value.toString(),match.child("teamA").value.toString(),match.child("teamB").value.toString()
                                ))
                                binding.liveMatchesrecy.adapter = LIveMatchesAdapter(list,this@PastMatches)
                            }
                        }
//                        val match:HashMap<Any,Any> = it.child("Match_details").getValue() as HashMap<Any, Any>
//
//                        Log.d("typedr", match!!::class.simpleName.toString())
                    }
//                    Log.d("sedr",list.size.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
    override fun onItemClick(match: Match) {
        val i = Intent(this,ScoreCard::class.java)
        val str=match.id+" "+match.teamA +" "+match.teamB
        i.putExtra("matchDetails",str)
        Toast.makeText(this,match.teamA+" vs "+match.teamB,Toast.LENGTH_LONG).show()
        startActivity(i)
    }
}
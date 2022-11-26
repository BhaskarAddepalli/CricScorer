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
import com.example.cricscorer.databinding.ActivityLiveMatchesBinding
import com.example.cricscorer.ui.Batsman
import com.google.firebase.database.*

class LiveMatches : AppCompatActivity(),SelectListener {
    lateinit var binding:ActivityLiveMatchesBinding
    lateinit var list:ArrayList<Match>
    lateinit var databaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_live_matches)

        binding= ActivityLiveMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.liveMatchesrecy.layoutManager=LinearLayoutManager(this)
        binding.liveMatchesrecy.setHasFixedSize(true)
        list= arrayListOf<Match>()
        getListData()
    }


    private fun getListData() {
        databaseref = FirebaseDatabase.getInstance().getReference("live_matches")

        databaseref.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (it in snapshot.children) {

                        val match:HashMap<Any,Any> = it.child("Match_details").getValue() as HashMap<Any, Any>

                        Log.d("typedr", match!!::class.simpleName.toString())
                        Log.d("typedrt",match.toString())
                        if (match != null && match["ongoing"]==true) {
                            list.add(Match(match["id"] as String, match["teamA"] as String,
                                match["teamB"] as String
                            ))
                        }
                    }
                    binding.liveMatchesrecy.adapter = LIveMatchesAdapter(list,this@LiveMatches)
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
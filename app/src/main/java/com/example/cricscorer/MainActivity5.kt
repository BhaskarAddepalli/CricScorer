package com.example.cricscorer

import Match
import android.app.Activity
import android.content.Intent
import com.example.cricscorer.ui.MainViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cricscorer.ui.MainViewModel
import com.example.cricscorer.R.layout
import com.example.cricscorer.databinding.ActivityMain5Binding
import com.example.cricscorer.databinding.BatsmanscoresBinding
import com.example.cricscorer.databinding.Middlescore1Binding
import com.example.cricscorer.databinding.TotalscoreBinding
import com.example.cricscorer.ui.Batsman
import com.example.cricscorer.ui.Bowler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity5 : AppCompatActivity() {
     lateinit var binding: TotalscoreBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var binding1: ActivityMain5Binding
    lateinit var binding2:BatsmanscoresBinding
    lateinit var binding3:Middlescore1Binding
   lateinit var firebasedatabase:FirebaseDatabase
   lateinit var databaseref:DatabaseReference
    lateinit var databaserefBatsman:DatabaseReference
    lateinit var databaserefBowler:DatabaseReference
    var c=1
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == 18){
                databaserefBowler.child(mainViewModel.bowlerc.value!!.name).setValue(mainViewModel.bowlerc.value)
//                Log.d("refesai",)

                val value = it.data?.getStringExtra("bowler_name")

                CoroutineScope(Dispatchers.Main).launch{ readData(value.toString())}

            }
            if(it.resultCode == 19){
                val value1 = it.data?.getStringExtra("out_batsman_name")
                val value2 = it.data?.getStringExtra("batsman_name")

                if(mainViewModel.batsman1.value!!.name ==value1.toString()){
                    databaserefBatsman.child(mainViewModel.batsman1.value!!.name).setValue(mainViewModel.batsman1.value)
                    var b=Batsman(value2.toString(),"0","0","0","0","0.0")
                    mainViewModel.batsman1.value=b
                }
                else{
                    databaserefBatsman.child(mainViewModel.batsman2.value!!.name).setValue(mainViewModel.batsman2.value)
                    var b=Batsman(value2.toString(),"0","0","0","0","0.0")
                    mainViewModel.batsman2.value=b
                }
            }


            if(it.resultCode == 20){
                val value1 = it.data?.getStringExtra("btk1")
                val value2 = it.data?.getStringExtra("btk1")
                val value3 = it.data?.getStringExtra("bwk1")

                mainViewModel.batsman1.value=Batsman(value1.toString(),"0","0","0","0","0.0")
                mainViewModel.batsman2.value=Batsman(value2.toString(),"0","0","0","0","0.0")
                mainViewModel.bowlerc.value=Bowler(value3.toString(),"0","0","0","0","0.0")

                databaserefBatsman.child(mainViewModel.batsman1.value!!.name).setValue(mainViewModel.batsman1.value)
                databaserefBatsman.child(mainViewModel.batsman2.value!!.name).setValue(mainViewModel.batsman2.value)
                databaserefBowler.child(mainViewModel.bowlerc.value!!.name).setValue(mainViewModel.bowlerc.value)


            }



        }
    override fun onCreate(savedInstanceState: Bundle?) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val current = formatter.format(date)
        super.onCreate(savedInstanceState)
        var str1="Match on $current"
//        Log.d("sdfkj",intent.getStringExtra("overs").toString())
        var overs=intent.getStringExtra("overs").toString()
        if(overs.length==0){
            overs="20"
        }
        var btsk=intent.getStringExtra("battingFirst").toString()
        var bowlingFirst=intent.getStringExtra("bfdg").toString()
        var teamAname=intent.getStringExtra("teamAname").toString()
        var teamBname=intent.getStringExtra("teamBname").toString()
        var obj=Match(str1.toString(),teamAname,teamBname)
        val stry="$str1 $teamAname $teamBname"
       databaseref=FirebaseDatabase.getInstance().getReference("live_matches").child(stry)
        databaseref.child("Match_details").setValue(obj).addOnSuccessListener {
            Log.d("sai","added match details data")
        }
        databaserefBatsman=databaseref.child("1st inning").child("batsman")
        databaserefBowler=databaseref.child("1st inning").child("bowler")
        Log.d("refesai",databaserefBowler.toString())

        val extras=intent.extras ?:return
        val bt1=extras.getString("bt1")
        val bt2=extras.getString("bt2")
        val bw=extras.getString("bw")
        Log.d("sia","hh")


        val btob1=Batsman(bt1.toString(),"0","0","0","0","0.00")
        val btob2=Batsman(bt2.toString(),"0","0","0","0","0.00")
        val bwob1=Bowler(bw.toString(),"0","0","0","0","0.00")

        mainViewModel=ViewModelProvider(this, MainViewModelFactory(btob1,btob2,bwob1)).get(MainViewModel::class.java)
        binding = setContentView(this, layout.totalscore)
        binding1= setContentView(this,layout.activity_main5)
        binding1.top.textView3.text=btsk+" 1st innings"
        mainViewModel.teamName.value=btsk +" 1st innings"
        binding1.lifecycleOwner=this
        mainViewModel.oversLimit=overs.toInt()-1
        Log.d("sadf",binding1.top.textView3.text.toString())
        binding1.top.textView3.text =btsk

        binding1.user=mainViewModel


        mainViewModel.overChange.observe(this, Observer<Boolean>{ it ->
            if(it ==true){
                val i = Intent(this, BowlerSelection::class.java)
                getResult.launch(i)
// Receiver
               mainViewModel.overChange.value=false
            }
        })

        mainViewModel.wicketFall.observe(this, Observer<Boolean>{ it ->
            if(it ==true){
                val i = Intent(this, BatterSelection::class.java)

                getResult.launch(i)
                // Receiver
                mainViewModel.wicketFall.value=false
            }
        })

        mainViewModel.inningsCompleted.observe(this, Observer<Boolean>{ it ->
            if(it ==true && mainViewModel.secondInnings==false){
                databaserefBatsman=FirebaseDatabase.getInstance().getReference("live_matches").child(stry).child("2nd inning").child("batsman")
                databaserefBowler=FirebaseDatabase.getInstance().getReference("live_matches").child(stry).child("2nd inning").child("bowler")
                val i = Intent(this, MainActivity31::class.java)
                getResult.launch(i)
                c=1
                binding1.top.textView3.text=bowlingFirst+" 2nd innings"
                mainViewModel.overs.value="0.0"
                mainViewModel.Score.value="0-0"
                mainViewModel.inningsCompleted.value=false
            }
        })

        mainViewModel.ballUpdate.observe(this, Observer<Int> {
            it ->
            updateDB()
        })

        mainViewModel.winningTeam.observe(this, Observer<Int> {
                it ->


//            var databaseref1 = FirebaseDatabase.getInstance().getReference("past_matches")
//            databaseref1.setValue(stry)
//            var databaseref2 = FirebaseDatabase.getInstance().getReference("live_matches")
//            databaseref2.child(stry).get().addOnSuccessListener {
//                if(it.exists()){
//                    Log.d("srrt",it.toString())
//                    databaseref1.child(stry).setValue(it)
//                }
//            }
            Log.d("is coming","is coming")
            var updateobj=HashMap<String,Any>()
            updateobj.put("id",str1.toString())
            updateobj.put("teamA",teamAname)
            updateobj.put("teamB",teamBname)
            updateobj.put("ongoing",false)
//                Match(str1.toString(),teamAname,teamBname,false)


            var i=Intent(this,SuccessActivity::class.java)

            if(it ==1){
                i.putExtra("winning_team",btsk)
                var databaseref1 = FirebaseDatabase.getInstance().getReference("past_matches")
                databaseref1.child(stry).setValue(stry)
                databaseref.child("Match_details").updateChildren(updateobj)
                startActivity(i)
            }
            else if(it ==2){
                i.putExtra("winning_team",bowlingFirst)
                var databaseref1 = FirebaseDatabase.getInstance().getReference("past_matches")
                databaseref1.child(stry).setValue(stry)
                databaseref.child("Match_details").updateChildren(updateobj)
                startActivity(i)
            }
            else if(it ==3){
                i.putExtra("winning_team","match tied")
                var databaseref1 = FirebaseDatabase.getInstance().getReference("past_matches")
                databaseref1.child(stry).setValue(stry)
                databaseref.child("Match_details").updateChildren(updateobj)
                startActivity(i)
            }

        })



    }

//resume add scoring
//    override fun onDestroy() {
//        super.onDestroy()
//        databaseref.child("striker").setValue(mainViewModel.batsman1.value)
//        databaseref.child("non-striker").setValue(mainViewModel.batsman2.value)
//        databaseref.child("bowler-current").setValue(mainViewModel.bowlerc.value)
//    }

    fun updateDB(){
        if(c==1) {
            c = 0
            databaserefBatsman.child(mainViewModel.batsman1.value!!.name).setValue(mainViewModel.batsman1.value)
            Log.d("updation",mainViewModel.batsman1.value.toString())
            databaserefBatsman.child(mainViewModel.batsman2.value!!.name).setValue(mainViewModel.batsman2.value)
            Log.d("updation",mainViewModel.batsman1.value.toString())
            databaserefBowler.child(mainViewModel.bowlerc.value!!.name).setValue(mainViewModel.bowlerc.value)
            Log.d("updation",mainViewModel.bowlerc.value.toString())
        }
        else{
            var x=mainViewModel.batsman1.value!!
            var btk1= HashMap<String,Any>()
            btk1.put("name",x.name)
            btk1.put("runs",x.Runs)
            btk1.put("balls",x.Balls)
            btk1.put("fours",x.fours)
            btk1.put("sixes",x.sixes)
            btk1.put("sr",x.sr)
            x=mainViewModel.batsman2.value!!
            var btk2= HashMap<String,Any>()
            btk2.put("name",x.name)
            btk2.put("runs",x.Runs)
            btk2.put("balls",x.Balls)
            btk2.put("fours",x.fours)
            btk2.put("sixes",x.sixes)
            btk2.put("sr",x.sr)
             var y=mainViewModel.bowlerc.value!!
            var bwk1=HashMap<String,Any>()
            bwk1.put("name",y.name)
            bwk1.put("maidens",y.maidens)
            bwk1.put("wickets",y.wickets)
            bwk1.put("runs",y.runs)
            bwk1.put("overs",y.overs)
            bwk1.put("er",y.ER)

            Log.d("updation2",mainViewModel.batsman1.value.toString())
            Log.d("updation2",mainViewModel.batsman2.value.toString())
            Log.d("updation2",mainViewModel.bowlerc.value.toString())
            databaserefBatsman.child(mainViewModel.batsman1.value!!.name).updateChildren(btk1)
            databaserefBatsman.child(mainViewModel.batsman2.value!!.name).updateChildren(btk2)
            databaserefBowler.child(mainViewModel.bowlerc.value!!.name).updateChildren(bwk1)
        }
    }


    suspend fun readData(str:String) {
        var job= CoroutineScope(Dispatchers.IO).launch {
            var bowl:Bowler= Bowler(str,"0","0","0","0","0.00")
            databaserefBowler.child(str).get().addOnSuccessListener {
                if (it.exists()) {
                    Log.d("bowl", it.value.toString())
                    bowl = Bowler(
                        it.child("name").value.toString(),
                        it.child("overs").value.toString(),
                        it.child("maidens").value.toString(),
                        it.child("runs").value.toString(),
                        it.child("wickets").value.toString(),
                        it.child("er").value.toString()
                    )

                }
                mainViewModel.bowlerc.value= bowl
                Log.d("sad1",mainViewModel.bowlerc.value.toString())
            }.addOnFailureListener{
                Log.d("bowlerRetrieve",it.toString())
            }
        }
        job.join()
    }






    //resume add scoring
//    fun readPastData(str:String) :Bowler?{
//        var bowl: Bowler? =null
//        databaseref.child(str).get().addOnSuccessListener {
//            if(it.exists()){
//                Log.d("past_batsman",it.value.toString())
//                bowl= it.value as Bowler
//            }
//
//        }
//        return bowl
//    }


}
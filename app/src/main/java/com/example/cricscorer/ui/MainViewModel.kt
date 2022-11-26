package com.example.cricscorer.ui

import android.util.Log



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(var bt1: Batsman, var bt2: Batsman, var bw1: Bowler) :ViewModel() {

    var penalty=MutableLiveData<String>("")
    var heading1=Batsman("Batsman","R","B","4s","6s","SR")
    var heading2=Bowler("Bowler","O","M","R","W","ER")
    var scoreAint:Int = 0
    var scoreBint:Int = 0
    var wicketsA:Int = 0
    var wicketsB:Int = 0
    var currrunrate =0.0
    var Score =MutableLiveData<String>("0-0")
    val Crr =MutableLiveData<String>("0.00")
    val overs =MutableLiveData<String>("0.0")
    var overNo=0;
    var ballNo=0;
    val teamName=MutableLiveData<String>("A, 1st inning")
    val b1=MutableLiveData<Boolean>(false)
    val b2=MutableLiveData<Boolean>(false)
    val b3=MutableLiveData<Boolean>(false)
    val b4=MutableLiveData<Boolean>(false)
    val b5=MutableLiveData<Boolean>(false)
    var overChange=MutableLiveData<Boolean>(false)
    var wicketFall=MutableLiveData<Boolean>(false)
     var oversLimit: Int=16
    var inningsCompleted=MutableLiveData<Boolean>(false)
    var ballUpdate=MutableLiveData<Int>(1)
    var winningTeam=MutableLiveData<Int>(0)
    var secondInnings=false
//    var x=bt1.
//    var y=bt2
//    var z=bw1

    var batsman1 =MutableLiveData<Batsman>(bt1)
    var batsman2 =MutableLiveData<Batsman>(bt2)
    var bowlerc =MutableLiveData<Bowler>(bw1)

    var ScoreA ="0-0"
    var CrrA ="0.00"
    var oversA ="0.0"

    var strike=1
    var overScore=0

     fun increaseScore(int: Int){

         if(penalty.value != ""){
             overScore+=penalty.value!!.toInt()
             scoreAint+=penalty.value!!.toInt()
             penalty.value=""
         }

         if(b2.value==false && b1.value==false){
             overScore+=int
             var x=bowlerc.value!!.runs.toInt()+int
             bowlerc.value!!.runs=x.toString()
             if(b5.value==true){
                 x=bowlerc.value!!.wickets.toInt()+1
                 bowlerc.value!!.wickets=x.toString()
             }
             var z='0'+ballNo+1
             var y= bowlerc.value!!.overs[0]+"."+z

             bowlerc.value!!.overs=y
             if((bowlerc.value!!.overs[0] - '0') * 6 + ballNo !=0) {
                 var er =
                     (bowlerc.value!!.runs.toFloat()) / ((bowlerc.value!!.overs[0] - '0') * 6 + ballNo+1)
                 bowlerc.value!!.ER=(er*6).toString()
                 Log.d("erdf1",er.toString())
             }
         }
         else{
             overScore+=int
             var x=bowlerc.value!!.runs.toInt()+int
             bowlerc.value!!.runs=x.toString()
             if(b5.value==true && b2.value==false){
                 x=bowlerc.value!!.wickets.toInt()+1
                 bowlerc.value!!.wickets=x.toString()
             }
//             var z='0'+ballNo+1
//             var y= bowlerc.value!!.overs[0]+"."+z
//             Log.d("sai",y)
//             bowlerc.value!!.overs=y
             if((bowlerc.value!!.overs[0] - '0') * 6 + ballNo !=0) {
                 var er =
                     (bowlerc.value!!.runs.toFloat()) / ((bowlerc.value!!.overs[0] - '0') * 6 + ballNo+1)
                 Log.d("erdf",er.toString())
                 bowlerc.value!!.ER=(er*6).toString()
             }
         }

         if(strike==1){
               var x=batsman1.value!!.Runs.toInt()
                x+=int
             batsman1.value!!.Runs=x.toString()
                 if(int ==4){
                     x=batsman1.value!!.fours.toInt()
                     x+=1
                     batsman1.value!!.fours=x.toString()

                 }
                 else if(int ==6){
                     x=batsman1.value!!.sixes.toInt()
                     x+=1
                     batsman1.value!!.sixes=x.toString()
                 }
                 if(b2.value==false && b1.value ==false){
                     x=batsman1.value!!.Balls.toInt()
                     x+=1
                     batsman1.value!!.Balls=x.toString()
                 }
             if(batsman1.value!!.Balls.toInt() != 0) {
                var xy = (batsman1.value!!.Runs.toFloat()) / (batsman1.value!!.Balls.toFloat())
                 batsman1.value!!.sr = (xy*100).toString()
             }
             batsman1.value=Batsman(batsman1.value!!.name,batsman1.value!!.Runs,batsman1.value!!.Balls,batsman1.value!!.fours,batsman1.value!!.sixes,batsman1.value!!.sr)

             if(int%2 !=0){
                 strike =0
             }
         }
         else{
             var x=batsman2.value!!.Runs.toInt()
             x+=int
             batsman2.value!!.Runs=x.toString()
             if(int ==4){
                 x=batsman2.value!!.fours.toInt()
                 x+=1
                 batsman2.value!!.fours=x.toString()

             }
             else if(int ==6){
                 x=batsman2.value!!.sixes.toInt()
                 x+=1
                 batsman2.value!!.sixes=x.toString()
             }
             if(b2.value==false && b1.value ==false){
                 x=batsman2.value!!.Balls.toInt()
                 x+=1
                 batsman2.value!!.Balls=x.toString()
             }
             if(batsman2.value!!.Balls.toInt() != 0) {
                 var xy = (batsman2.value!!.Runs.toFloat()) / (batsman2.value!!.Balls.toFloat())
                 batsman2.value!!.sr = (xy*100).toString()
             }

             batsman2.value=Batsman(batsman2.value!!.name,batsman2.value!!.Runs,batsman2.value!!.Balls,batsman2.value!!.fours,batsman2.value!!.sixes,batsman2.value!!.sr)
             if(int%2 !=0){
                 strike =1
             }
         }

         scoreAint+=int
         ballUpdate.value=1- ballUpdate.value!!
         if(b1.value == true) {
             increaseScoreb1()
         }
         else if(b2.value == true) {
             increaseScoreb2()
         }
         if(b2.value ==false && b5.value == true) {
             increaseWickets()
             wicketFall.value=true
         }
         if(b1.value ==true || b2.value ==true){
           ballNo+=0
         }
         else{
             ballNo+=1
             if(ballNo ==6){
                 ballNo=0
                 overNo+=1
                 strike=1-strike
                 if(overScore==0){
                     bowlerc.value!!.maidens=(bowlerc.value!!.maidens.toInt()+1).toString()
                 }
                 overChange.value = !(overNo>oversLimit || wicketsA>=10)

//                 if(overNo>oversLimit || wicketsA>=10){
//                     overChange.value=false
//                 }
//                 else {
//                     overChange.value = true
//                 }
                 var d=bowlerc.value!!.overs[0]+1
                 bowlerc.value!!.overs=d+".0"
                 overScore=0

             }
         }
         bowlerc.value=Bowler(bowlerc.value!!.name,bowlerc.value!!.overs,bowlerc.value!!.maidens,bowlerc.value!!.runs,bowlerc.value!!.wickets,bowlerc.value!!.ER)
         b1.value=false
         b2.value=false
         b3.value=false
         b4.value=false
         b5.value=false
         updateScore()

     }

    fun increaseScoreb1(){
            scoreAint += 1
    }
    fun increaseScoreb2() {
        scoreAint += 1
    }

    fun increaseWickets(){
            wicketsA+=1

    }

    fun updateScore(){
        ScoreA= Score.value!!
        oversA= overs.value!!
        CrrA=Crr.value!!

        Score.value = "${scoreAint}-${wicketsA}"
        overs.value ="(${overNo}.${ballNo})"
        if(overNo ==0 && ballNo ==0){
            Crr.value =(scoreAint*6).toString()
        }
        else {
            Crr.value = (((scoreAint) * 6 / (overNo * 6 + ballNo)).toString())
        }
        if(overNo>oversLimit || wicketsA>=10){
            if(secondInnings){
                if(scoreAint >scoreBint){
                    winningTeam.value=2
                }
                else if(scoreAint <scoreBint){
                    winningTeam.value=1
                }
                else{
                    winningTeam.value=3
                }
            }
            overNo = 0
            scoreBint=scoreAint
            scoreAint=0
            inningsCompleted.value=true
            secondInnings=true
        }
        if(secondInnings){
            if(scoreAint >scoreBint) {
                winningTeam.value = 2
            }
        }

    }

    fun undo(){
        Score.value=ScoreA
        overs.value=oversA
        Crr.value=CrrA
    }


}




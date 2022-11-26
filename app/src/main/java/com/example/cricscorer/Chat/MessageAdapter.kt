package com.example.cricscorer.Chat

import Message
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val cone: Context, var listMssg:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


   val ITEM_RECV=1
    val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType ==1){
            val v:View=LayoutInflater.from(cone).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(v)
        }
        else{
            val v:View=LayoutInflater.from(cone).inflate(R.layout.sent,parent,false)
            return SentViewHolder(v)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.javaClass ==SentViewHolder::class.java){
            val viewHolder=holder as SentViewHolder
            holder.sentMessg.text=listMssg[position].message
        }else{
            val viewHolder=holder as ReceiveViewHolder
            holder.rcvMessg.text=listMssg[position].message
            holder.username.text=listMssg[position].senderName

        }

    }

    override fun getItemViewType(position: Int): Int {

        val currentMssg =listMssg[position]

        if( FirebaseAuth.getInstance().currentUser?.uid.equals(currentMssg.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECV
        }
    }

    override fun getItemCount(): Int {
        return listMssg.size
    }



    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessg=itemView.findViewById<TextView>(R.id.sentmssg)
    }

    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val rcvMessg=itemView.findViewById<TextView>(R.id.rcvmssg)
        val username=itemView.findViewById<TextView>(R.id.username)
    }

}
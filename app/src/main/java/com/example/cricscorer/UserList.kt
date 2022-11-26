package com.example.cricscorer

import User
import UserListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserList : AppCompatActivity() {
    lateinit var  userRecy:RecyclerView
    lateinit var userlst:ArrayList<User>
    lateinit var adapter: UserListAdapter
    lateinit var mauth:FirebaseAuth
    lateinit var databaseref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        mauth= FirebaseAuth.getInstance()
        userRecy=findViewById(R.id.recycleuser)
        userRecy.layoutManager=LinearLayoutManager(this)
        databaseref=FirebaseDatabase.getInstance().getReference()
        userlst= ArrayList()

        adapter=UserListAdapter(this,userlst)
        userRecy.adapter=adapter

        databaseref.child("Users").addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userlst.clear()
                for( snap in snapshot.children){
                    val cur=snap.getValue(User::class.java)
                    if(cur?.uid!= mauth.currentUser?.uid) {
                        userlst.add(cur!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.logout){
            mauth.signOut()
            finish()
            val i=Intent(this@UserList,MainActivityneg1::class.java)
            startActivity(i)
            return true
        }
        return true


    }
}
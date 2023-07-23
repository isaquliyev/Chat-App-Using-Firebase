package com.example.chatapprealtime

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapprealtime.adapter.ChatUserAdapter
import com.example.chatapprealtime.adapter.ChatroomAdapter
import com.example.chatapprealtime.adapter.OnItemChatClickListener
import com.example.chatapprealtime.adapter.OnItemClickListener
import com.example.chatapprealtime.databinding.ActivityMainBinding
import com.example.chatapprealtime.model.ChatroomModel
import com.example.chatapprealtime.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    var chatroomList = mutableListOf<ChatroomModel>(
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
    )
    var chatList = mutableListOf<User>()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        databaseReference = Firebase.database.reference

        Picasso.get()
            .load(auth.currentUser?.photoUrl)
            .transform(CropCircleTransformation())
            .into(binding.imageView)

        binding.usernameId.text = auth.currentUser?.displayName
        binding.chatroomRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.chatroomRV.adapter = ChatroomAdapter(chatroomList, object : OnItemClickListener {
            override fun onItemClick() {
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                startActivity(intent)
            }
        })
        getData()
        binding.singleChatRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.singleChatRV.adapter = ChatUserAdapter(chatList, object : OnItemChatClickListener {
            override fun onItemClick(user: User) {
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                intent.putExtra("User", user)
                startActivity(intent)
            }
        })
    }

    fun getData() {
        databaseReference.child("User").addChildEventListener(object : ChildEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {

                    if (!auth.currentUser?.email.toString().equals(snapshot.getValue<User>()?.email.toString())) {
                        chatList.add(snapshot.getValue<User>() as User)
                    }


                    binding.singleChatRV.adapter?.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}
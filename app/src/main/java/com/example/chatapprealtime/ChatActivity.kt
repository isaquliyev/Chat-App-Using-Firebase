package com.example.chatapprealtime

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapprealtime.adapter.ChatAdapter
import com.example.chatapprealtime.databinding.ActivityChatBinding
import com.example.chatapprealtime.model.Message
import com.example.chatapprealtime.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var messages: MutableList<Message>
    private lateinit var secondUser: User
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        secondUser = intent.getSerializableExtra("User") as User
        auth = Firebase.auth
        messages = mutableListOf()


        Picasso.get()
            .load(auth.currentUser?.photoUrl)
            .transform(CropCircleTransformation())
            .into(binding.currentUserImageID)
        binding.currentUserNameID.text = auth.currentUser?.displayName.toString()
        Picasso.get()
            .load(secondUser.photoUrl)
            .transform(CropCircleTransformation())
            .into(binding.SecondUserImageID)
        binding.secondUserNameID.text = secondUser.name


        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = layoutManager

        adapter = ChatAdapter(messages, auth.currentUser?.uid.toString())
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
        })
        binding.messageRecyclerView.adapter = adapter

        binding.sendID.setOnClickListener {
            if (binding.chatEditTextID.text.toString().trim().isNotEmpty()) {
                databaseReference.child("Messages")
                    .child("${auth.currentUser?.uid.toString()}-${secondUser.uid}")
                    .push()
                    .setValue(
                        Message(
                            auth.currentUser?.uid.toString(),
                            secondUser.uid,
                            binding.chatEditTextID.text.toString().trim(),
                        )
                    )
                databaseReference.child("Messages")
                    .child("${secondUser.uid}-${auth.currentUser?.uid.toString()}")
                    .push()
                    .setValue(
                        Message(
                            auth.currentUser?.uid.toString(),
                            secondUser.uid,
                            binding.chatEditTextID.text.toString().trim(),
                        )
                    )
                binding.chatEditTextID.setText("")
            }
        }
        getData()
    }

    private fun getData() {
        databaseReference.child("Messages")
            .child("${auth.currentUser?.uid.toString()}-${secondUser.uid}")
            .addChildEventListener( object : ChildEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if(snapshot.exists()) {
                        messages.add(snapshot.getValue<Message>() as Message)
                        binding.messageRecyclerView.adapter?.notifyDataSetChanged()
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
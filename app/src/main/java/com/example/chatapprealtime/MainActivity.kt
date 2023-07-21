package com.example.chatapprealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapprealtime.adapter.ChatAdapter
import com.example.chatapprealtime.adapter.ChatroomAdapter
import com.example.chatapprealtime.adapter.OnItemClickListener
import com.example.chatapprealtime.databinding.ActivityMainBinding
import com.example.chatapprealtime.model.ChatroomModel
import com.example.chatapprealtime.model.SingleChatModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var chatroomList = mutableListOf<ChatroomModel>(
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
        ChatroomModel("Bozenka", "Malina"),
    )
    var chatList = mutableListOf<SingleChatModel>(
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        SingleChatModel("Bozenka", "example@gmail.com"),
        )
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get()
            .load(FirebaseAuth.getInstance().currentUser?.photoUrl)
            .transform(CropCircleTransformation())
            .into(binding.imageView)

        binding.usernameId.text = FirebaseAuth.getInstance().currentUser?.displayName
        binding.chatroomRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.chatroomRV.adapter = ChatroomAdapter(chatroomList, object : OnItemClickListener {
            override fun onItemClick() {
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                startActivity(intent)
            }
        })
        binding.singleChatRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.singleChatRV.adapter = ChatAdapter(chatList, object : OnItemClickListener {
            override fun onItemClick() {
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
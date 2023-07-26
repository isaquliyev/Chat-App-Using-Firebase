package com.example.chatapprealtime.adapter

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapprealtime.databinding.ChatRowBinding
import com.example.chatapprealtime.model.Message

class ChatAdapter(var list : List <Message> , var currentUid : String ) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder( val binding : ChatRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list[position].senderUid.equals(currentUid)) {
            holder.binding.receiverLinearLayout.visibility = View.GONE
            holder.binding.senderLinearLayout.visibility = View.VISIBLE
            holder.binding.sentMessageID.text = list[position].message
        }
        else{
            holder.binding.senderLinearLayout.visibility = View.GONE
            holder.binding.receiverLinearLayout.visibility = View.VISIBLE
            holder.binding.receivedMessageID.text = list[position].message
        }
    }

}
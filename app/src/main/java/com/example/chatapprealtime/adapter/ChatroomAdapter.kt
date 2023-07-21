package com.example.chatapprealtime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapprealtime.R
import com.example.chatapprealtime.model.ChatroomModel

class ChatroomAdapter(var list : List<ChatroomModel>, val listener : OnItemClickListener) : RecyclerView.Adapter<ChatroomAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameId = itemView.findViewById<TextView>(R.id.nameId)
        var surnameId = itemView.findViewById<TextView>(R.id.surnameId)
        var gradientBg = itemView.findViewById<ImageView>(R.id.gradientBG)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chatroom_recycler_row, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameId.setText(list.get(position).name)
        holder.surnameId.setText(list.get(position).surname)
        holder.gradientBg.setOnClickListener {
            listener.onItemClick()
        }
    }
}

interface OnItemClickListener {
    fun onItemClick()
}
package com.example.chatapprealtime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapprealtime.R
import com.example.chatapprealtime.model.SingleChatModel

class ChatAdapter( val list : List<SingleChatModel>, val listener: OnItemClickListener): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameId = itemView.findViewById<TextView>(R.id.nameId2)
        var email = itemView.findViewById<TextView>(R.id.emainId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_recycler_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameId.setText(list.get(position).name)
        holder.email.setText(list.get(position).email)
        holder.itemView.setOnClickListener {
            listener.onItemClick()
        }
    }
}

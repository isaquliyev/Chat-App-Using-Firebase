package com.example.chatapprealtime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapprealtime.R
import com.example.chatapprealtime.model.User
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ChatUserAdapter(val list : List<User>, val listener: OnItemChatClickListener): RecyclerView.Adapter<ChatUserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameId = itemView.findViewById<TextView>(R.id.nameId2)
        var email = itemView.findViewById<TextView>(R.id.emainId)
        var profileImage = itemView.findViewById<ImageView>(R.id.profileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserAdapter.ViewHolder {
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
        Picasso.get()
            .load(list.get(position).photoUrl)
            .transform(CropCircleTransformation())
            .into(holder.profileImage)
        holder.itemView.setOnClickListener {
            listener.onItemClick(list[position])
        }
    }
}


interface OnItemChatClickListener {
    fun onItemClick(user: User)
}

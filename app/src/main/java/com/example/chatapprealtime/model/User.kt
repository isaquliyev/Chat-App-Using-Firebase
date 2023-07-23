package com.example.chatapprealtime.model

import java.io.Serializable

data class User(
    val name: String? = "",
    val photoUrl: String? = "",
    val uid: String? = "",
    val email: String? = ""
) : Serializable

package com.example.loginapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val password: String
    // Add other fields for your user data here
    // For example:
    // val username: String,
    // val email: String
)
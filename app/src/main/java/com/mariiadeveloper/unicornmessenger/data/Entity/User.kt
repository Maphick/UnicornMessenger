package com.mariiadeveloper.unicornmessenger.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "name",

    @ColumnInfo(name = "username")
    val username: String = "username",

    @ColumnInfo(name = "avatar")
    val avatar: String = "avatar",

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String= "phoneNumber",

    @ColumnInfo(name = "nickname")
    val nickname: String= "nickname",

    @ColumnInfo(name = "city")
    val city: String = "city",

    @ColumnInfo(name = "instagram")
val instagram: String= "instagram",

    @ColumnInfo(name = "vk")
    val vk: String= "vk",

    @ColumnInfo(name = "status")
    val status: String= "status",

    @ColumnInfo(name = "dateOfBirth")
    val dateOfBirth: String= "dateOfBirth",

    @ColumnInfo(name = "zodiacSign")
    val zodiacSign: String= "zodiacSign",

    @ColumnInfo(name = "about")
    val about: String= "about",
)

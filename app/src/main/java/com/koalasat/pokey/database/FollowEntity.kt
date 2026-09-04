package com.koalasat.pokey.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "follow",
    indices = [
        Index(
            value = ["hexPub", "followedPub"],
            name = "follow_unique_hexPub_followedPub",
            unique = true,
        ),
    ],
)
data class FollowEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hexPub: String,
    val followedPub: String,
    var createdAt: Long = 0L,
)

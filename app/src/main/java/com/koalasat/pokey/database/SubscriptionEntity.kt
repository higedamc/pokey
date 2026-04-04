package com.koalasat.pokey.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "subscription",
    indices = [Index(value = ["value"], unique = true)],
)
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val value: String,
    val type: String,
    val label: String?,
    val enabled: Int = 1,
    val createdAt: Long,
)

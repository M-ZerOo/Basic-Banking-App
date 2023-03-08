package com.melfouly.basicbanking.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val currentBalance: Double
) : Parcelable

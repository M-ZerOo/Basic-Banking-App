package com.melfouly.basicbanking.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long,
    val sender: String,
    val receiver: String,
    val amount: Double
) : Parcelable

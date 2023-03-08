package com.melfouly.basicbanking.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melfouly.basicbanking.domain.Transaction

@Entity("transaction_table")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val sender: String,
    val receiver: String,
    val amount: Double
)

fun List<TransactionEntity>.asDomainModel(): List<Transaction> {
    return map {
        Transaction(
            id = it.id!!,
            sender = it.sender,
            receiver = it.receiver,
            amount = it.amount
        )
    }
}

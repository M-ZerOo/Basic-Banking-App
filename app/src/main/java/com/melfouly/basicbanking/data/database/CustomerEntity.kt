package com.melfouly.basicbanking.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melfouly.basicbanking.domain.Customer

@Entity("Customers_table")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val email: String,
    @ColumnInfo("phone_number")
    val phoneNumber: String,
    @ColumnInfo("current_balance")
    val currentBalance: Double
)

// Change List of CustomerEntity to List of Customers.
fun List<CustomerEntity>.asDomainModel(): List<Customer> {
    return map {
        Customer(
            id = it.id,
            name = it.name,
            email = it.email,
            phoneNumber = it.phoneNumber,
            currentBalance = it.currentBalance
        )
    }
}

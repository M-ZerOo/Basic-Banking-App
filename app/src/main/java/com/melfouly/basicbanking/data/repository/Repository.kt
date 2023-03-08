package com.melfouly.basicbanking.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.melfouly.basicbanking.data.database.BankDatabase
import com.melfouly.basicbanking.data.database.CustomerEntity
import com.melfouly.basicbanking.data.database.TransactionEntity
import com.melfouly.basicbanking.data.database.asDomainModel
import com.melfouly.basicbanking.domain.Customer
import com.melfouly.basicbanking.domain.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: BankDatabase) {

    private var receivers = emptyList<String>()

    // Getting all customers from database and manipulate the data to return
    // a liveData list of Customer data class.
    val customers: LiveData<List<Customer>> = Transformations.map(
        database.customersDao().getAllCustomers()
    ) { it.asDomainModel() }

    // Getting all transactions from database and manipulate the data to return
    // a liveData list of Transaction data class.
    val transactions: LiveData<List<Transaction>> = Transformations.map(
        database.transactionDao().getAllTransactions()
    ) { it.asDomainModel() }

    // Getting a list of the available receivers based on the sender name, which
    // gets all data from the database.
    suspend fun receiversList(customerIdException: Long): List<String> {
        withContext(Dispatchers.IO) {
            val receivedList = database.customersDao().getAllCustomersExcept(customerIdException)
            receivers = receivedList.map { it.name }
        }
        return receivers
    }

    // Updates the balance of the sender and receiver on the database based on the amount,
    // and inserts the new transaction to the database.
    suspend fun makeTransaction(sender: String, receiver: String, amount: Double) {
        withContext(Dispatchers.IO) {
            val senderData = database.customersDao().getCustomerByName(sender)
            val updatedSenderData =
                senderData.copy(currentBalance = senderData.currentBalance - amount)
            Log.d("TAG", "New Sender: ${updatedSenderData.currentBalance}")
            database.customersDao().updateCustomer(updatedSenderData)

            val receiverData = database.customersDao().getCustomerByName(receiver)
            val updatedReceiverData =
                receiverData.copy(currentBalance = receiverData.currentBalance + amount)
            Log.d("TAG", "New Receiver: ${updatedReceiverData.currentBalance}")
            database.customersDao().updateCustomer(updatedReceiverData)

            val transaction =
                TransactionEntity(id = null, sender = sender, receiver = receiver, amount = amount)
            database.transactionDao().insertTransaction(transaction)
        }
    }


}

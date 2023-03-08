package com.melfouly.basicbanking.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.melfouly.basicbanking.data.database.BankDatabase.Companion.getDatabase
import com.melfouly.basicbanking.data.repository.Repository

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = Repository(database)

    var transactions = repository.transactions
}
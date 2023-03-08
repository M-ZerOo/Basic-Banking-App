package com.melfouly.basicbanking.ui.customers.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melfouly.basicbanking.data.database.BankDatabase
import com.melfouly.basicbanking.data.repository.Repository
import com.melfouly.basicbanking.domain.Customer
import kotlinx.coroutines.launch

class CustomerDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val database = BankDatabase.getDatabase(application)
    private val repository = Repository(database)

    private val _availableReceivers = MutableLiveData<List<String>>(emptyList())
    val availableReceivers: LiveData<List<String>> get() = _availableReceivers

    private var selectedCustomerName = ""

    fun getReceiversReady(customerIdException: Long) {
        viewModelScope.launch {
            _availableReceivers.value = repository.receiversList(customerIdException)
        }
    }

    fun saveSelectedCustomer(customerName: String) {
        selectedCustomerName = customerName
    }

    fun makeTransaction(sender: String, amount: Double) {
        viewModelScope.launch {
            repository.makeTransaction(sender, selectedCustomerName, amount)
        }

    }

}
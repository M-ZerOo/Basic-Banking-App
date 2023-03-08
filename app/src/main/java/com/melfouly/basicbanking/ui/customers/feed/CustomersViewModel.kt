package com.melfouly.basicbanking.ui.customers.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melfouly.basicbanking.data.database.BankDatabase
import com.melfouly.basicbanking.data.database.BankDatabase.Companion.getDatabase
import com.melfouly.basicbanking.data.repository.Repository
import com.melfouly.basicbanking.domain.Customer
import kotlinx.coroutines.launch

class CustomersViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = Repository(database)

    var customers = repository.customers

    private val _detailedCustomer = MutableLiveData<Customer?>()
    val detailedCustomer: LiveData<Customer?> get() = _detailedCustomer

    // Save the value of the customer clicked to be observed later.
    fun onCustomerClicked(customer: Customer) {
        _detailedCustomer.value = customer
    }

    // Event for navigating to detailedCustomerFragment successfully and make the value
    // of _detailedCustomer null.
    fun doneNavigating() {
        _detailedCustomer.value = null
    }
}
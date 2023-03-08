package com.melfouly.basicbanking.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.melfouly.basicbanking.data.database.CustomerEntity

@Dao
interface CustomersDao {
    @Query("select * from customers_table")
    fun getAllCustomers(): LiveData<List<CustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCustomers(vararg customer: CustomerEntity)

    @Update
    suspend fun updateCustomer(customer: CustomerEntity)

    @Query("select * from customers_table where id <> :customerId")
    fun getAllCustomersExcept(customerId: Long): List<CustomerEntity>

    @Query("select * from customers_table where id = :id")
    fun getCustomerById(id: Long): LiveData<CustomerEntity>

    @Query("select * from customers_table where name = :name")
    fun getCustomerByName(name: String): CustomerEntity
}
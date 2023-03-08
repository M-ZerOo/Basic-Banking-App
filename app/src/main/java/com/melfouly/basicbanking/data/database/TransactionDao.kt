package com.melfouly.basicbanking.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {
    @Query("select * from transaction_table order by id desc")
    fun getAllTransactions(): LiveData<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionEntity)
}
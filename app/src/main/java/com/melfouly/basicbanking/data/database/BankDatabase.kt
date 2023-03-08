package com.melfouly.basicbanking.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch

@Database(
    entities = [CustomerEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BankDatabase : RoomDatabase() {
    abstract fun customersDao(): CustomersDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: BankDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): BankDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BankDatabase::class.java,
                    "bank_database"
                ).addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    database.customersDao().insertAllCustomers(*dummyCustomers)
                                }
                            }
                        }
                    }
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

private val dummyCustomers = arrayOf(
    CustomerEntity(1, "Mahmoud Reda", "mahmoudreda@gmail.com", "000000000000", 2000.0),
    CustomerEntity(2, "Mohamed Ahmed", "mohamedahmed@gmail.com", "01111111111", 5000.0),
    CustomerEntity(3, "Khaled Ali", "khalidali@gmail.com", "02222222222", 7500.0),
    CustomerEntity(4, "Ziad Abdo", "ziadabdo@gmail.com", "03333333333", 8600.0),
    CustomerEntity(5, "Mazen Rabea", "mazenrabea@gmail.com", "04444444444", 9000.0),
    CustomerEntity(6, "Gamal Mohamed", "gamalmohamed@gmail.com", "05555555555", 10050.0),
    CustomerEntity(7, "Zaki Mahmoud", "zakimahmoud@gmail.com", "06666666666", 6000.0),
    CustomerEntity(8, "Hazem Rady", "hazemrady@gmail.com", "07777777777", 11000.0),
    CustomerEntity(9, "Ayman moamen", "Aymanmoamen@gmail.com", "08888888888", 3000.0),
    CustomerEntity(10, "Amr Mostafa", "amrmostafa@gmail.com", "09999999999", 30000.0)
)


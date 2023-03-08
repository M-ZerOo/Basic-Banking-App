package com.melfouly.basicbanking.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.melfouly.basicbanking.R
import com.melfouly.basicbanking.domain.Customer
import java.text.NumberFormat
import java.util.*

// Loads the balance of the customer with the defined currency which is Egyptian pound.
@BindingAdapter("loadBalance")
fun bindTextViewToDisplayBalance(textView: TextView, balance: Double) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.maximumFractionDigits = 2
    numberFormat.currency = Currency.getInstance("EGP")
    textView.text = numberFormat.format(balance)
}
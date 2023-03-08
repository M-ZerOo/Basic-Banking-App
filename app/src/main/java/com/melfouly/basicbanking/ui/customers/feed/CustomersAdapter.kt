package com.melfouly.basicbanking.ui.customers.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melfouly.basicbanking.databinding.CustomerItemListBinding
import com.melfouly.basicbanking.domain.Customer

class CustomersAdapter(private val clickListener: ItemClickListener) :
    ListAdapter<Customer, CustomersAdapter.CustomerViewHolder>(DiffCallback()) {

    class CustomerViewHolder(private val binding: CustomerItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer, clickListener: ItemClickListener) {
            binding.customer = customer
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomerItemListBinding.inflate(layoutInflater, parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

    class ItemClickListener(val clickListener: (customer: Customer) -> Unit) {
        fun onClick(customer: Customer) = clickListener(customer)
    }
}
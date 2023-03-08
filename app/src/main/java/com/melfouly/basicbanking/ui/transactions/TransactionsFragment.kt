package com.melfouly.basicbanking.ui.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.melfouly.basicbanking.R
import com.melfouly.basicbanking.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private val viewModel: TransactionsViewModel by lazy {
        ViewModelProvider(this)[TransactionsViewModel::class.java]
    }
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentTransactionsBinding.inflate(layoutInflater)

        // To support onBackPressed and navigate to HomeFragment.
        this.findNavController().enableOnBackPressed(true)

        adapter = TransactionsAdapter()
        binding.recyclerView.adapter = adapter

        // Observing the transaction liveData and submit any change to the adapter.
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            transactions?.let { adapter.submitList(it) }
        }

        return binding.root
    }

}
package com.melfouly.basicbanking.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.melfouly.basicbanking.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Clicking on customers button navigates to CustomersFragment.
        binding.customersButton.setOnClickListener {
            this.findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCustomersFragment()
            )
        }

        // Clicking on transactions button navigates to TransactionsFragment.
        binding.transactionButton.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTransactionsFragment()
            )
        }

        return binding.root
    }


}
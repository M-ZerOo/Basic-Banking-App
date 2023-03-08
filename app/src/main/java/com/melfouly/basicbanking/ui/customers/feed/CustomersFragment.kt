package com.melfouly.basicbanking.ui.customers.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.melfouly.basicbanking.databinding.FragmentCustomersBinding


class CustomersFragment : Fragment() {

    private lateinit var binding: FragmentCustomersBinding
    private val viewModel: CustomersViewModel by lazy {
        ViewModelProvider(this)[CustomersViewModel::class.java]
    }
    private lateinit var adapter: CustomersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentCustomersBinding.inflate(layoutInflater)

        // To support onBackPressed and navigate to HomeFragment.
        this.findNavController().enableOnBackPressed(true)

        adapter = CustomersAdapter(CustomersAdapter.ItemClickListener { customer ->
            viewModel.onCustomerClicked(customer)
        })

        binding.recyclerView.adapter = adapter

        // Observing the customers liveData and submit any change to the adapter.
        viewModel.customers.observe(viewLifecycleOwner) { customers ->
            customers?.let { adapter.submitList(it) }
        }

        // Observing the detailedCustomer. Once clicking on a customer it navigates to
        // CustomerDetailsFragment and fires an event once it's done and returns the value to null again.
        viewModel.detailedCustomer.observe(viewLifecycleOwner) { customer ->
            customer?.let {
                this.findNavController().navigate(
                    CustomersFragmentDirections.actionCustomersFragmentToCustomerDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        }

        return binding.root
    }


}
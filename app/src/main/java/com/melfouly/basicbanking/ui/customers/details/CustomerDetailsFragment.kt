package com.melfouly.basicbanking.ui.customers.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.melfouly.basicbanking.R
import com.melfouly.basicbanking.databinding.FragmentCustomerDetailsBinding

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private val viewModel: CustomerDetailsViewModel by lazy {
        ViewModelProvider(this)[CustomerDetailsViewModel::class.java]
    }
    private lateinit var receiverAdapter: ArrayAdapter<String>
    private lateinit var receiversList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentCustomerDetailsBinding.inflate(layoutInflater)

        this.findNavController().enableOnBackPressed(true)

        val selectedCustomer =
            CustomerDetailsFragmentArgs.fromBundle(requireArguments()).selectedCustomer
        viewModel.getReceiversReady(selectedCustomer.id)

        binding.selectedCustomer = selectedCustomer

        viewModel.availableReceivers.observe(viewLifecycleOwner) {
            receiversList = it
            receiverAdapterImpl()
        }

        binding.transferButton.setOnClickListener {
            val transferredAmount = binding.amountEdittext.text.toString().toDouble()
            if (transferredAmount > selectedCustomer.currentBalance) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.transfer_amount_greater_than),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.makeTransaction(selectedCustomer.name, transferredAmount)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.transaction_successful),
                    Toast.LENGTH_SHORT
                ).show()
                this.findNavController().navigate(
                    CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToCustomersFragment()
                )
            }
        }

        return binding.root
    }

    private fun receiverAdapterImpl() {
        receiverAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            receiversList
        )

        binding.sentToAutocomplete.setAdapter(receiverAdapter)

        binding.sentToAutocomplete.setOnItemClickListener { _, _, position, _ ->
            val customerName = receiverAdapter.getItem(position)
            viewModel.saveSelectedCustomer(customerName!!)
        }

    }


}
package com.example.nellymakeup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nellymakeup.R
import com.example.nellymakeup.application.hide
import com.example.nellymakeup.application.show
import com.example.nellymakeup.data.model.Order
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.databinding.FragmentOrderBinding
import com.example.nellymakeup.ui.adapters.MyOrdersListAdapter
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_order, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        getMyOrdersList()
    }

    private fun getMyOrdersList() {

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getMyOrdersList(this@OrderFragment)
    }
    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        hideProgressDialog()

        if (ordersList.size > 0) {

            binding.rvMyOrderItems.show()
            binding.tvNoOrdersFound.hide()

            binding.rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
            binding.rvMyOrderItems.adapter = myOrdersAdapter
        } else {
            binding.rvMyOrderItems.hide()
            binding.tvNoOrdersFound.show()
        }

    }
}
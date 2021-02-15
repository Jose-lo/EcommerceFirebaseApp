package com.example.nellymakeup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nellymakeup.R
import com.example.nellymakeup.application.hide
import com.example.nellymakeup.application.show
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.databinding.FragmentHomeBinding
import com.example.nellymakeup.ui.adapters.DashboardItemsListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :BaseFragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }


    private fun getDashboardItemsList() {

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getDashboardItemsList(this@HomeFragment)
    }

    fun successHomeItemsList(dashboardItemsList: ArrayList<Product>) {

        hideProgressDialog()

        if (dashboardItemsList.size > 0) {

            binding.rvDashboardItems.show()
            binding.tvNoDashboardItemsFound.hide()
            binding.rvDashboardItems.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            binding.rvDashboardItems.adapter = adapter
        } else {
            binding.rvDashboardItems.hide()
            binding.tvNoDashboardItemsFound.show()
        }
    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }
}
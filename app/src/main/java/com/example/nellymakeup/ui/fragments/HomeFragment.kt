package com.example.nellymakeup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nellymakeup.R
import com.example.nellymakeup.application.Resource
import com.example.nellymakeup.application.hide
import com.example.nellymakeup.application.show
import com.example.nellymakeup.application.toast
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.databinding.FragmentHomeBinding
import com.example.nellymakeup.presentation.HomeScreenViewModel
import com.example.nellymakeup.presentation.ViewModelFactory
import com.example.nellymakeup.repository.RepositoryImpl
import com.example.nellymakeup.ui.adapters.DashboardItemsListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :BaseFragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewmodel by viewModels<HomeScreenViewModel> { ViewModelFactory(RepositoryImpl(
        RemoteDataSource(FirestoreClass())
    )) }

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
        successHomeItemsList()
    }

    fun successHomeItemsList(){
        viewmodel.fetchDashboardList().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    showProgressDialog("Loading...")
                }
                is Resource.Success->{
                    if(it.data.isEmpty()){
                        hideProgressDialog()
                        binding.emptyDashboardContainer.root.show()
                        return@Observer
                    }
                    hideProgressDialog()
                    binding.rvDashboardItems.layoutManager = GridLayoutManager(activity, 2,RecyclerView.VERTICAL,false)
                    binding.rvDashboardItems.adapter = DashboardItemsListAdapter(requireContext(), it.data)

                }
                is Resource.Failure->{
                    hideProgressDialog()
                    toast("Hubo un error: ${it.exception}")
                }
            }
        })
    }
}
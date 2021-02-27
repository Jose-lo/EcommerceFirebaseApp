package com.example.nellymakeup.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nellymakeup.R
import com.example.nellymakeup.application.Constants
import com.example.nellymakeup.application.goToActivity
import com.example.nellymakeup.application.preferences
import com.example.nellymakeup.data.model.User
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.databinding.ActivitySettingsBinding
import com.example.nellymakeup.presentation.SettingsScreenViewModel
import com.example.nellymakeup.presentation.ViewModelFactory
import com.example.nellymakeup.repository.RepositoryImpl
import com.example.nmkup.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    private val viewModel by viewModels<SettingsScreenViewModel> { ViewModelFactory(RepositoryImpl(
        RemoteDataSource(FirestoreClass())
    )) }
    private lateinit var binding:ActivitySettingsBinding
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        btnLogout()
        btnEdit()
        btnAddress()
    }
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarSettingsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding.toolbarSettingsActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getUserDetails() {

        showProgressDialog(resources.getString(R.string.please_wait))
        viewModel.getUserDetails(this)
    }

    fun userDetailsSuccess(user: User) {

        mUserDetails = user

        hideProgressDialog()

        GlideLoader(this@SettingsActivity).loadUserPicture(user.image, binding.ivUserPhoto)

        binding.tvName.text = "${user.firstName} ${user.lastName}"
        binding.tvGender.text = user.gender
        binding.tvEmail.text = user.email
        binding.tvMobileNumber.text = "${user.mobile}"

    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    private fun btnLogout(){
        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            preferences?.saveStatusLogin(false)
            goToActivity<MainActivity> { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            finish()
        }
    }

    private fun btnEdit(){
        tv_edit.setOnClickListener {
            goToActivity<UserProfileActivity> { putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails) }
        }
    }

    private fun btnAddress(){
        ll_address.setOnClickListener {
            goToActivity<AddressListActivity>()
        }
    }


}
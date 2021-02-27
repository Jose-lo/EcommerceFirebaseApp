package com.example.nellymakeup.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nellymakeup.R
import com.example.nellymakeup.application.Constants
import com.example.nellymakeup.application.hide
import com.example.nellymakeup.application.show
import com.example.nellymakeup.application.toast
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.databinding.ActivityAddEditAddressBinding
import com.example.nellymakeup.presentation.AddAddressSreenViewModel
import com.example.nellymakeup.presentation.ViewModelFactory
import com.example.nellymakeup.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_add_edit_address.*

class AddEditAddressActivity : BaseActivity() {

    private val viewmodel by viewModels<AddAddressSreenViewModel> { ViewModelFactory(RepositoryImpl(
        RemoteDataSource(FirestoreClass())
    )) }
    private lateinit var binding: ActivityAddEditAddressBinding
    private var mAddressDetails: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_address)
        binding = ActivityAddEditAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)) {
            mAddressDetails =
                intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)!!
        }

        if (mAddressDetails != null) {
            if (mAddressDetails!!.id.isNotEmpty()) {

                binding.tvTitle.text = resources.getString(R.string.title_edit_address)
                binding.btnSubmitAddress.text = resources.getString(R.string.btn_lbl_update)

                binding.etFullName.setText(mAddressDetails?.name)
                binding.etPhoneNumber.setText(mAddressDetails?.mobileNumber)
                binding.etAddress.setText(mAddressDetails?.address)
                binding.etZipCode.setText(mAddressDetails?.zipCode)
                binding.etAdditionalNote.setText(mAddressDetails?.additionalNote)

                when (mAddressDetails?.type) {
                    Constants.HOME -> {
                        rb_home.isChecked = true
                    }
                    Constants.OFFICE -> {
                        rb_office.isChecked = true
                    }
                    else -> {
                        binding.rbOther.isChecked = true
                        binding.tilOtherDetails.show()
                        binding.etOtherDetails.setText(mAddressDetails?.otherDetails)
                    }
                }
            }
        }

        setupActionBar()
        btnSubmitAddress()

        binding.rgType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_other) {
                binding.tilOtherDetails.show()
            } else {
                binding.tilOtherDetails.hide()
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarAddEditAddressActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarAddEditAddressActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(binding.etFullName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_full_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etPhoneNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_phone_number),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etAddress.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_address), true)
                false
            }

            TextUtils.isEmpty(binding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }

            binding.rbOther.isChecked && TextUtils.isEmpty(
                binding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun saveAddressToFirestore() {

        val fullName: String = binding.etFullName.text.toString().trim { it <= ' ' }
        val phoneNumber: String = binding.etPhoneNumber.text.toString().trim { it <= ' ' }
        val address: String = binding.etAddress.text.toString().trim { it <= ' ' }
        val zipCode: String = binding.etZipCode.text.toString().trim { it <= ' ' }
        val additionalNote: String = binding.etAdditionalNote.text.toString().trim { it <= ' ' }
        val otherDetails: String = binding.etOtherDetails.text.toString().trim { it <= ' ' }

        if (validateData()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val addressType: String = when {
                binding.rbHome.isChecked -> {
                    Constants.HOME
                }
                binding.rbOffice.isChecked -> {
                    Constants.OFFICE
                }
                else -> {
                    Constants.OTHER
                }
            }

            val addressModel = Address(
                FirestoreClass().getCurrentUserID(),
                fullName,
                phoneNumber,
                address,
                zipCode,
                additionalNote,
                addressType,
                otherDetails
            )

            if (mAddressDetails != null && mAddressDetails!!.id.isNotEmpty()) {
                FirestoreClass().updateAddress(
                    this@AddEditAddressActivity,
                    addressModel,
                    mAddressDetails!!.id
                )
            } else {
                viewmodel.addAddress(this,addressModel)
            }
        }
    }


    fun addUpdateAddressSuccess() {

        hideProgressDialog()

        val notifySuccesMessage : String = if (mAddressDetails != null && mAddressDetails!!.id.isNotEmpty()){
            resources.getString(R.string.msg_your_address_updated_successfully)
        }else{
            resources.getString(R.string.err_your_address_added_successfully)
        }
        toast(notifySuccesMessage)
        finish()
    }

    private fun btnSubmitAddress(){
        btn_submit_address.setOnClickListener {
            saveAddressToFirestore()
        }
    }
}
package com.example.nellymakeup.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.nellymakeup.R
import com.example.nellymakeup.application.toast
import com.example.nellymakeup.data.model.User
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToRegisterActivity()
        setupActionBar()
        btnRegistering()
    }

    private fun goToRegisterActivity(){
        tv_login.setOnClickListener {
            onBackPressed()
        }
    }

    private fun btnRegistering(){
        btn_register.setOnClickListener {
            registerUser()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarRegisterActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener{ onBackPressed()}
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            binding.etPassword.text.toString().trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.registery_successfull), false)
                true
            }
        }
    }

    private fun registerUser() {
        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        hideProgressDialog()

                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                    firebaseUser.uid,
                                    binding.etFirstName.text.toString().trim { it <= ' ' },
                                    binding.etLastName.text.toString().trim { it <= ' ' },
                                    binding.etEmail.text.toString().trim { it <= ' ' }
                            )

                            FirestoreClass().registerUser(this@RegisterActivity,user)

                        } else {
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    fun userRegistrationSuccess() {
        hideProgressDialog()
        toast(resources.getString(R.string.register_success))
    }
}
package com.example.nellymakeup.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.example.nellymakeup.R
import com.example.nellymakeup.application.goToActivity
import com.example.nellymakeup.application.toast
import com.example.nellymakeup.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var binding:ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpForgotPassword()
        backActivity()

    }

    private fun backActivity(){
        binding.imgForgotPasswordBack.setOnClickListener {
            goToActivity<LoginActivity> { finish() }
        }
    }

    private fun setUpForgotPassword(){

        btn_submit.setOnClickListener {

            val email: String = binding.etEmailForgot.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {

                showProgressDialog(resources.getString(R.string.please_wait))

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        hideProgressDialog()

                        if (task.isSuccessful) {
                            toast( resources.getString(R.string.email_sent_success))
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }

    }

}
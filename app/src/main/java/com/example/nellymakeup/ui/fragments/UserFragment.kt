package com.example.nellymakeup.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nellymakeup.R
import com.example.nellymakeup.application.*
import com.example.nellymakeup.databinding.FragmentUserBinding
import com.example.nellymakeup.ui.activities.LoginActivity
import com.example.nellymakeup.ui.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        if(preferences!!.readLoginStatus()){
            binding.containerLogin.hide()
            binding.containerWelcome.show()
            setNAme()
            btnLogout()
        }else{
            binding.containerLogin.show()
            binding.containerWelcome.hide()
            btnLogin()
        }
    }


    private fun btnLogin(){
        btn_pass_login.setOnClickListener {
            goToActivity<LoginActivity>()
        }
    }

    private fun btnLogout(){
        btn_user_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            preferences?.saveStatusLogin(false)
            goToActivity<MainActivity> { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        }
    }

    private fun setNAme(){
        val sharedPreferences =
            context?.getSharedPreferences(Constants.NELLYMAKEUP_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString(Constants.LOGGED_IN_USERNAME, "")!!
        binding.txtSetUsername.setText(username)
    }

}
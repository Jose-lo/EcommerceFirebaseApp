package com.example.nellymakeup.application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: CharSequence,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(),message,duration).show()
fun Activity.toast(message: CharSequence,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()
fun Context.toast(message: CharSequence,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <reified T : Activity>Activity.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this, T ::class.java)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Activity>Fragment.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(activity, T ::class.java)
    intent.init()
    startActivity(intent)
}
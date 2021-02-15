package com.example.nellymakeup.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nellymakeup.R
import com.example.nellymakeup.application.*
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.databinding.ActivityCartListBinding
import com.example.nellymakeup.ui.adapters.CartItemsListAdapter
import kotlinx.android.synthetic.main.activity_cart_list.*

class CartListActivity : BaseActivity() {

    private lateinit var binding:ActivityCartListBinding
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        btnCheckOut()
    }
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCartListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarCartListActivity.setNavigationOnClickListener { onBackPressed() }
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {

        hideProgressDialog()

        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {

                    cart.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cart.cart_quantity = product.stock_quantity
                    }
                }
            }
        }

        mCartListItems = cartList

        if (cartList.size > 0) {

            binding.rvCartItemsList.show()
            binding.llCheckout.show()
            binding.tvNoCartItemFound.hide()

            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
            binding.rvCartItemsList.setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity, mCartListItems, true)
            binding.rvCartItemsList.adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in mCartListItems) {


                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }
            }

                binding.tvSubTotal.text = "$$subTotal"
                binding.tvShippingCharge.text = "$10.0"

                if (subTotal > 0) {
                    binding.llCheckout.show()

                    val total = subTotal + 10
                    binding.tvTotalAmount.text = "$$total"
                } else {
                    binding.llCheckout.hide()
                }

            } else {
                binding.rvCartItemsList.hide()
                binding.llCheckout.hide()
                binding.tvNoCartItemFound.show()
            }

        }

        private fun getCartItemsList() {

            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getCartList(this@CartListActivity)
        }

        override fun onResume() {
            super.onResume()
            getProductList()
        }

        fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

            hideProgressDialog()
            mProductsList = productsList
            getCartItemsList()
        }

        private fun getProductList() {

            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getAllProductsList(this@CartListActivity)
        }

    fun itemRemovedSuccess() {

        hideProgressDialog()
        toast( resources.getString(R.string.msg_item_removed_successfully))
        getCartItemsList()
    }
    fun itemUpdateSuccess() {

        hideProgressDialog()
        getCartItemsList()
    }

    private fun btnCheckOut(){
        btn_checkout.setOnClickListener {
            goToActivity<AddressListActivity> { putExtra(Constants.EXTRA_SELECT_ADDRESS, true) }
        }
    }

}
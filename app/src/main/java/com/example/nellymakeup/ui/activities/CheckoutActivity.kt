package com.example.nellymakeup.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nellymakeup.R
import com.example.nellymakeup.application.*
import com.example.nellymakeup.data.model.Address
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Order
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.databinding.ActivityCheckoutBinding
import com.example.nellymakeup.presentation.CheckoutScreenViewModel
import com.example.nellymakeup.presentation.ViewModelFactory
import com.example.nellymakeup.repository.RepositoryImpl
import com.example.nellymakeup.ui.adapters.CartItemsListAdapter
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : BaseActivity() {

    private val viewmodel by viewModels<CheckoutScreenViewModel> { ViewModelFactory(RepositoryImpl(
        RemoteDataSource(FirestoreClass())
    )) }

    private lateinit var binding: ActivityCheckoutBinding
    private var mAddressDetails: Address? = null
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<CartItem>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails =
                intent.getParcelableExtra<Address>(Constants.EXTRA_SELECTED_ADDRESS)!!
        }

        if (mAddressDetails != null) {
            binding.tvCheckoutAddressType.text = mAddressDetails?.type
            binding.tvCheckoutFullName.text = mAddressDetails?.name
            binding.tvCheckoutAddress.text = "${mAddressDetails!!.address}, ${mAddressDetails!!.zipCode}"
            binding.tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            binding.tvCheckoutMobileNumber.text = mAddressDetails?.mobileNumber
        }

        getProductList()
        btnPalceOrder()
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCheckoutActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding.toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductList() {

        showProgressDialog(resources.getString(R.string.please_wait))

        viewmodel.getAllProductsList(this)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        mProductsList = productsList
        getCartItemsList()
    }

    private fun getCartItemsList() {
        viewmodel.getCartList(this)
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {

        hideProgressDialog()
        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }
        mCartItemsList = cartList

        binding.rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        binding.rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
        binding.rvCartListItems.adapter = cartListAdapter



        for (item in mCartItemsList) {

            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                mSubTotal += (price * quantity)
            }
        }

        binding.tvCheckoutSubTotal.text = "$$mSubTotal"
        binding.tvCheckoutShippingCharge.text = "$10.0"

        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.show()

            mTotalAmount = mSubTotal + 10.0
            binding.tvCheckoutTotalAmount.text = "$$mTotalAmount"
        } else {
            binding.llCheckoutPlaceOrder.hide()
        }
    }

    private fun placeAnOrder() {

        showProgressDialog(resources.getString(R.string.please_wait))

        if (mAddressDetails != null) {
            val order = Order(
                FirestoreClass().getCurrentUserID(),
                mCartItemsList,
                mAddressDetails!!,
                "My order ${System.currentTimeMillis()}",
                mCartItemsList[0].image,
                mSubTotal.toString(),
                "10.0",
                mTotalAmount.toString(),
                System.currentTimeMillis()
            )
            FirestoreClass().placeOrder(this@CheckoutActivity, order)
        }
    }


    fun orderPlacedSuccess() {
        FirestoreClass().updateAllDetails(this@CheckoutActivity, mCartItemsList)
    }

    private fun btnPalceOrder(){
        btn_place_order.setOnClickListener {
            placeAnOrder()
        }
    }

    fun allDetailsUpdatedSuccessfully() {

        hideProgressDialog()
        toast("Your order placed successfully.")
        goToActivity<MainActivity> { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        finish()

    }
}
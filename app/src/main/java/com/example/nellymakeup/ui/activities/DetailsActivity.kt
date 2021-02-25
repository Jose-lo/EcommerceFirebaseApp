package com.example.nellymakeup.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.nellymakeup.R
import com.example.nellymakeup.application.*
import com.example.nellymakeup.data.model.CartItem
import com.example.nellymakeup.data.model.Product
import com.example.nellymakeup.data.remote.FirestoreClass
import com.example.nellymakeup.data.remote.RemoteDataSource
import com.example.nellymakeup.databinding.ActivityDetailsBinding
import com.example.nellymakeup.presentation.DetailScreenModel
import com.example.nellymakeup.presentation.ViewModelFactory
import com.example.nellymakeup.repository.RepositoryImpl
import com.example.nmkup.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity(), View.OnClickListener {

    private val viewmodel by viewModels<DetailScreenModel> { ViewModelFactory(RepositoryImpl(
        RemoteDataSource(FirestoreClass())
    )) }
    private lateinit var binding:ActivityDetailsBinding
    private var mProductID : String = ""
    private lateinit var mProductDetails: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(Constants.EXTRA_PRODUCT_ID)){
            mProductID = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }
        var productOwnerId: String = ""

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        if (FirestoreClass().getCurrentUserID() == productOwnerId) {
            binding.btnAddToCart.hide()
            binding.btnGoToCart.hide()
        } else {
            binding.btnAddToCart.show()
        }


        getProductDetails()
        binding.btnAddToCart.setOnClickListener (this)
        binding.btnGoToCart.setOnClickListener(this)
        backActivity()
    }

    private fun backActivity(){
        binding.imgDetailBack.setOnClickListener {
            goToActivity<MainActivity> { finish() }
        }
    }

    private fun getProductDetails() {

        showProgressDialog(resources.getString(R.string.please_wait))
        viewmodel.getProductDetails(this,mProductID)
    }

    fun productDetailsSuccess(product: Product) {

        mProductDetails = product
        hideProgressDialog()

        GlideLoader(this@DetailsActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.descriptionLarge
        binding.tvProductDetailsAvailableQuantity.text = product.stock_quantity

        if(product.stock_quantity.toInt() == 0){

            hideProgressDialog()

            binding.btnAddToCart.hide()

            binding.tvProductDetailsAvailableQuantity.text =
                resources.getString(R.string.lbl_out_of_stock)

            binding.tvProductDetailsAvailableQuantity.setTextColor(
                ContextCompat.getColor(
                    this@DetailsActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            if (FirestoreClass().getCurrentUserID() == product.user_id) {
                hideProgressDialog()
            } else {
                FirestoreClass().checkIfItemExistInCart(this@DetailsActivity, mProductID)
            }
        }
    }
    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {

                R.id.btn_add_to_cart -> {
                    if(preferences!!.readLoginStatus()){
                        addToCart()
                    }else{
                        goToActivity<LoginActivity>()
                    }
                }
                R.id.btn_go_to_cart -> {
                    goToActivity<CartListActivity>()
                }
            }
        }
    }

    private fun addToCart() {

        val addToCart = CartItem(
            FirestoreClass().getCurrentUserID(),
            mProductID,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        showProgressDialog(resources.getString(R.string.please_wait))
        viewmodel.addCartItems(this,addToCart)
    }

    fun addToCartSuccess() {

        hideProgressDialog()
        toast(resources.getString(R.string.success_message_item_added_to_cart))

        binding.btnAddToCart.hide()
        binding.btnGoToCart.show()
    }

    fun productExistsInCart() {

        hideProgressDialog()
        binding.btnAddToCart.hide()
        binding.btnGoToCart.show()
    }

}
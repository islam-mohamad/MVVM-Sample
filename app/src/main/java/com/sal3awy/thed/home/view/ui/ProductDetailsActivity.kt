package com.sal3awy.thed.home.view.ui

import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.ViewTreeObserver
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sal3awy.thed.R
import com.sal3awy.thed.databinding.ActivityProductDetailsBinding
import com.sal3awy.thed.home.model.entity.Product

class ProductDetailsActivity : AppCompatActivity() {

    private var binding: ActivityProductDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
            supportPostponeEnterTransition()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        binding?.view = this
        if (intent.hasExtra("product")) {
            binding?.product = intent.getSerializableExtra("product") as Product
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding?.ivProduct?.viewTreeObserver?.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding?.ivProduct!!.viewTreeObserver.removeOnPreDrawListener(this)
                        supportStartPostponedEnterTransition()
                        return true
                    }
                }
            )
        }
    }

    fun close(){
        onBackPressed()
    }
}

package com.sal3awy.thed.home.view.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sal3awy.thed.App
import com.sal3awy.thed.R
import com.sal3awy.thed.base.BaseActivity
import com.sal3awy.thed.binding.AppRecyclerViewAdapter
import com.sal3awy.thed.dagger.home.DaggerHomeComponent
import com.sal3awy.thed.dagger.home.HomeComponent
import com.sal3awy.thed.dagger.home.HomeModule
import com.sal3awy.thed.databinding.ActivityHomeBinding
import com.sal3awy.thed.home.model.entity.Product
import com.sal3awy.thed.home.view.callback.ProductCallback
import com.sal3awy.thed.home.viewmodel.ProductViewModel
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration




class HomeActivity : BaseActivity<ActivityHomeBinding>(), ProductCallback {
    override val layoutId: Int
        get() = R.layout.activity_home

    @Inject
    lateinit var viewModel: ProductViewModel

    private var mAdapter: AppRecyclerViewAdapter<Product>? = null
    private val productsList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
        setupRecyclerView()
        observeLoading()
        observeErrorMessage()
        viewModel.getProducts()
        observeViewModel()
    }

    private fun initInjection() {
        val component: HomeComponent = DaggerHomeComponent.builder()
            .homeModule(HomeModule(this))
            .appComponent(App.getComponent())
            .build()
        component.injectHomeActivity(this)
    }

    private val observerProducts = Observer<List<Product>> { products ->
        if (products!!.isNotEmpty()) {
            productsList.clear()
            productsList.addAll(products)
            mAdapter!!.submitList(productsList)
        }
    }


    private fun observeViewModel() {
        viewModel.getProductsLiveData().observe(this, observerProducts)
    }

    private fun setupRecyclerView() {
        mAdapter = AppRecyclerViewAdapter(this, object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.name == newItem.name
                        && oldItem.productDescription == newItem.productDescription
                        && oldItem.image.link.equals(newItem.image.link)
            }

        })

        mAdapter!!.setItemViewType(R.layout.item_product)
        val recyclerView = viewDataBinding!!.recyclerViewProducts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    override fun productClicked(product: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun observeErrorMessage() {
        viewModel.errorMessage.observe(this, Observer<String> { message ->
            if (!TextUtils.isEmpty(message)) {
                showSnakeBar(message)
            }
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(this, Observer<Boolean> { isLoading ->
            if (isLoading != null && isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

}
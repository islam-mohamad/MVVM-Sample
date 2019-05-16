package com.sal3awy.thed.home.view.ui


import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sal3awy.thed.R
import com.sal3awy.thed.base.BaseFragment
import com.sal3awy.thed.binding.AppRecyclerViewAdapter
import com.sal3awy.thed.databinding.FragmentHomeBinding
import com.sal3awy.thed.home.model.entity.Product
import com.sal3awy.thed.home.view.callback.ProductCallback
import com.sal3awy.thed.home.viewmodel.ProductViewModel
import javax.inject.Inject

class HomeFragment @Inject constructor (): BaseFragment<FragmentHomeBinding, ProductViewModel>(),
    ProductCallback {


    @Inject
    lateinit var viewModel: ProductViewModel

    override val layoutId: Int = R.layout.fragment_home

    override fun viewModel(): ProductViewModel {
       return viewModel
    }


    private var mAdapter: AppRecyclerViewAdapter<Product>? = null
    private val productsList = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts()
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    override fun productClicked(product: Product) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        } else {
            startActivity(intent)
        }
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
        val recyclerView = mViewDataBinding!!.recyclerViewProducts
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

}

package com.sal3awy.thed.home.view.ui

import android.os.Bundle
import com.sal3awy.thed.R
import com.sal3awy.thed.base.BaseActivity
import com.sal3awy.thed.databinding.ActivityHomeBinding
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    @Inject
    lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.homeFragment, homeFragment, HomeFragment::class.java.name).commit()
    }

    override fun getLayoutId(): Int = R.layout.activity_home

}
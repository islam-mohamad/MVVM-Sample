package com.sal3awy.thed.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.sal3awy.thed.R
import com.sal3awy.thed.networking.NetworkEvent
import com.sal3awy.thed.networking.NetworkState
import com.sal3awy.thed.utils.CommonUtils
import io.reactivex.functions.Consumer


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null
    var viewDataBinding: T? = null
        private set

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        //        performDependencyInjection();
        super.onCreate(savedInstanceState)
        performDataBinding()
        mProgressBar = CommonUtils.showProgressBar(this, viewDataBinding?.root as ViewGroup)
    }


    /*
     * register the BaseActivity as subscriber
     * and specify what needs to be done in case of
     * NO_INTERNET, NO_RESPONSE, UNAUTHORIZED error responses
     */
    override fun onResume() {
        super.onResume()
        NetworkEvent.register(this, Consumer {
            when (it) {
                NetworkState.NO_INTERNET -> displayErrorDialog(
                    getString(R.string.no_internet_title),
                    getString(R.string.no_internet_desc)
                )

                NetworkState.NO_RESPONSE -> displayErrorDialog(
                    getString(R.string.http_error_title),
                    getString(R.string.http_error_desc)
                )

                NetworkState.UNAUTHORIZED -> {
                    //redirect to login screen - if session expired
                   /* Toast.makeText(applicationContext, R.string.error_login_expired, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LgoinActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)*/
                }
            }
        })
    }

    /*
   * unregister the activity once it is finished.
   */
    override fun onStop() {
        super.onStop()
        NetworkEvent.unregister(this)
    }

    /*
   * just displaying an error
   * dialog here! But you configure whatever
   * you want
   */
    fun displayErrorDialog(
        title: String,
        desc: String
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(desc)
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        mProgressBar!!.visibility = View.GONE
    }

    fun openActivityOnTokenExpire() {
        //        startActivity(LoginActivity.newIntent(this));
        //        finish();
    }

    //    public void performDependencyInjection() {
    //        AndroidInjection.inject(this);
    //    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun showLoading() {
        mProgressBar!!.visibility = View.VISIBLE
    }

    fun showSnakeBar(message: String) {
        val snackBar = Snackbar.make(viewDataBinding!!.root, message, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        snackBar.show()
    }

    fun setFullScreen() {
        requestWindowFeature(FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding!!.executePendingBindings()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}
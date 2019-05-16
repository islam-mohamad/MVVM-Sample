package com.sal3awy.thed.binding

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sal3awy.thed.R
import com.squareup.picasso.Picasso


object CustomBindingAdapter {
    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView?, url: String) {
        if (view != null && !TextUtils.isEmpty(url)) {
            Picasso.with(view.context).load(url.replace("http", "https").trim())
                .fit()
                .placeholder(R.drawable.ic_image_broken)
                .error(R.drawable.ic_image_broken)
                .into(view)
        }
    }

    @BindingAdapter("height")
    @JvmStatic
    fun setHeight(view: View?, height: Double) {
        view?.let {
            view.layoutParams.height = height.toInt()
        }
    }

    @BindingAdapter("width")
    @JvmStatic
    fun setWidth(view: View?, width: Double?) {
        view?.let {
            view.layoutParams.width = width!!.toInt()
        }

    }
}
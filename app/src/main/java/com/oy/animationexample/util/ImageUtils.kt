package com.oy.animationexample.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.oy.animationexample.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val DEFAULT_IMAGE = R.drawable.no_image

@SuppressLint("UnrememberedMutableState")
@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // show default image while image loads
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .centerCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    // get network image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .centerCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    return bitmapState
}
@SuppressLint("UnrememberedMutableState")
@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // get network image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .centerCrop()
        .error(DEFAULT_IMAGE)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                Log.d(TAG, "onResourceReady: ${resource}")
                bitmapState.value = resource
            }
        })
    return bitmapState
}

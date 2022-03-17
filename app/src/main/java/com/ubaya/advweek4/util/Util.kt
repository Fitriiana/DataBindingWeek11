package com.ubaya.advweek4.util

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ktx.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.notification_action_background)
}
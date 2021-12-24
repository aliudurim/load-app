package com.udacity

import android.animation.AnimatorSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat

fun AnimatorSet.disableViewDuringAnimation(view: View) = apply {
    doOnStart { view.isEnabled = false }
    doOnEnd { view.isEnabled = true }
}

fun Context.getNotificationManager(): NotificationManager = ContextCompat.getSystemService(
    this,
    NotificationManager::class.java
) as NotificationManager

fun Context.getDownloadManager(): DownloadManager = ContextCompat.getSystemService(
    this,
    DownloadManager::class.java
) as DownloadManager
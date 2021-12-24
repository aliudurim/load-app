package com.udacity

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val fileName by lazy {
        intent?.extras?.getString(EXTRA_FILE_NAME, unknownText) ?: unknownText
    }
    private val downloadStatus by lazy {
        intent?.extras?.getString(EXTRA_DOWNLOAD_STATUS, unknownText) ?: unknownText
    }

    private val unknownText by lazy { getString(R.string.unknown) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
            .apply {
                setSupportActionBar(toolbar)
                initializeView()
            }
    }

    private fun ActivityDetailBinding.initializeView() {
        detailContent.fileNameText.text = fileName
        detailContent.downloadStatusText.text = downloadStatus
        detailContent.okButton.setOnClickListener { finish() }
        changeViewForDownloadStatus()
    }

    private fun ActivityDetailBinding.changeViewForDownloadStatus() {
        when (detailContent.downloadStatusText.text) {
            DownloadStatus.SUCCESSFUL.statusText -> {
                changeDownloadStatusImageTo(R.drawable.ic_check_circle_outline_24)
                changeDownloadStatusColorTo(R.color.colorPrimaryDark)
            }
            DownloadStatus.FAILED.statusText -> {
                changeDownloadStatusImageTo(R.drawable.ic_error_24)
                changeDownloadStatusColorTo(R.color.design_default_color_error)
            }
        }
    }

    private fun ActivityDetailBinding.changeDownloadStatusImageTo(@DrawableRes imageRes: Int) {
        detailContent.downloadStatusImage.setImageResource(imageRes)
    }

    private fun ActivityDetailBinding.changeDownloadStatusColorTo(@ColorRes colorRes: Int) {
        ContextCompat.getColor(this@DetailActivity, colorRes)
            .also { color ->
                detailContent.downloadStatusImage.imageTintList = ColorStateList.valueOf(color)
                detailContent.downloadStatusText.setTextColor(color)
            }
    }

    companion object {
        private const val EXTRA_FILE_NAME = "${BuildConfig.APPLICATION_ID}.FILE_NAME"
        private const val EXTRA_DOWNLOAD_STATUS = "${BuildConfig.APPLICATION_ID}.DOWNLOAD_STATUS"

        fun bundleExtrasOf(
            fileName: String,
            downloadStatus: DownloadStatus
        ) = bundleOf(
            EXTRA_FILE_NAME to fileName,
            EXTRA_DOWNLOAD_STATUS to downloadStatus.statusText
        )
    }
}

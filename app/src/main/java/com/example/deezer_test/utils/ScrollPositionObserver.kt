package com.example.deezer_test.utils

import android.content.Context
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.ScrollView
import com.example.deezer_test.R
import kotlin.math.min


/**
 * The class description here.
 *
 * @author thomas
 */
class ScrollPositionObserver(private val imageView: ImageView, private val mScrollView:ScrollView, context:Context): ViewTreeObserver.OnScrollChangedListener {

    private val mImageViewHeight = context.resources.getDimensionPixelSize(R.dimen.image_scroll)

    override fun onScrollChanged() {
        val scrollY = min(Math.max(mScrollView.scrollY, 0), imageView.height)

        imageView.translationY = (scrollY / 2).toFloat()

    }
}
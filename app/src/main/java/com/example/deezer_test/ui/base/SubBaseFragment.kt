package com.example.deezer_test.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection

import javax.inject.Inject

/**
 * The class description here.
 *
 * @author thomas
 */
abstract class SubBaseFragment : Fragment() {

    @Inject
    protected lateinit var mViewModeFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        FragmentManager = fragmentManager

    }

    companion object {
        var FragmentManager: FragmentManager? = null
    }

}
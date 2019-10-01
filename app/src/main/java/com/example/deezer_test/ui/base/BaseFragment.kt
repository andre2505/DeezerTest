package com.example.deezer_test.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * The class description here.
 *
 * @author Thomas Drège
 */
abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var mViewModeFactory: ViewModelProvider.Factory

    private lateinit var mBaseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        activity?.let { theActivity ->
            mBaseViewModel = ViewModelProviders.of(this, mViewModeFactory).get(BaseViewModel::class.java)
        }

        mBaseViewModel.mIsErrorNetwork.observe(this, Observer { theErrorNetwork ->
            if(theErrorNetwork) {
                Toast.makeText(context, "ERROR NETWORK", Toast.LENGTH_LONG).show()
            }
        })

        mBaseViewModel.mIsError.observe(this, Observer { theError ->
            if(theError) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }
}
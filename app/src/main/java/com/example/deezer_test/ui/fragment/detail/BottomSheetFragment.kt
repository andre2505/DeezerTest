package com.example.deezer_test.ui.fragment.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deezer_test.R
import com.example.deezer_test.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class BottomSheetFragment : BottomSheetDialogFragment() {

    @Inject
    protected lateinit var mViewModeFactory: ViewModelProvider.Factory

    private lateinit var mDetailViewModel: DetailViewModel

    private lateinit var mBottomSheetBinding: FragmentBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        activity?.let { theActivity ->
            mDetailViewModel = ViewModelProviders.of(theActivity, mViewModeFactory).get(DetailViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)

        mBottomSheetBinding.detailViewModel = mDetailViewModel
        mBottomSheetBinding.lifecycleOwner = this@BottomSheetFragment

        mDetailViewModel.mAlbum.observe(this@BottomSheetFragment, Observer { theAlbum ->
            Glide
                .with(context!!)
                .load(theAlbum.coverMedium)
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_disc)
                )
                .into(mBottomSheetBinding.bottomSheetAlbumImage)

        })
        // Inflate the layout for this fragment
        return mBottomSheetBinding.root
    }

}

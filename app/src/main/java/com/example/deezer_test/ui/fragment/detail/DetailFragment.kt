package com.example.deezer_test.ui.fragment.detail


import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deezer_test.R
import com.example.deezer_test.databinding.FragmentDetailBinding
import com.example.deezer_test.listener.CustomOnItemClickListener
import com.example.deezer_test.model.Album
import com.example.deezer_test.model.Track
import com.example.deezer_test.ui.activity.main.MainActivity
import com.example.deezer_test.ui.adapter.ListTracksAdapter
import com.example.deezer_test.ui.base.BaseFragment
import com.example.deezer_test.utils.ScrollPositionObserver
import com.metagalactic.dotprogressbar.DotProgressBar


/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class DetailFragment : BaseFragment(), CustomOnItemClickListener, View.OnClickListener {

    private val ALBUM = "album"

    private lateinit var mAlbum: Album

    private lateinit var mDetailFragmentDetailBinding: FragmentDetailBinding

    private lateinit var mDotProgressBar: DotProgressBar

    private lateinit var mDetailViewModel: DetailViewModel

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mViewAdapter: RecyclerView.Adapter<*>

    private lateinit var mViewManager: RecyclerView.LayoutManager

    private lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mTracksListAdapter: ListTracksAdapter

    private lateinit var mScrollView: ScrollView

    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { theActivity ->
            mDetailViewModel = ViewModelProviders.of(theActivity, mViewModeFactory).get(DetailViewModel::class.java)
        }
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAlbum = arguments?.getSerializable(ALBUM) as Album

        mDetailFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        mDotProgressBar = mDetailFragmentDetailBinding.detailProgressbar

        mRecyclerView = mDetailFragmentDetailBinding.recyclerView

        mScrollView = mDetailFragmentDetailBinding.listTrackScrollview

        mDetailFragmentDetailBinding.detailViewModel = mDetailViewModel

        mDetailFragmentDetailBinding.lifecycleOwner = this@DetailFragment

        mDetailViewModel.mAlbum.value = this.mAlbum

        mDetailFragmentDetailBinding.detailMoreIcon.setOnClickListener(this)

        Glide
            .with(this.context!!)
            .load(mAlbum.coverMedium)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_disc)
            )
            .into(mDetailFragmentDetailBinding.detailImageAlbum)


        val intent = Intent(MainActivity.RECEIVER_ARTIST)

        intent.putExtra(MainActivity.EXTRA_IMAGE, mAlbum.artist?.pictureSmall)
        intent.putExtra(MainActivity.EXTRA_NAME, mAlbum.artist?.name)

        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(intent)


        mDetailViewModel.getTracksRequest(mAlbum.id)

        mLinearLayoutManager = LinearLayoutManager(activity)

        mViewManager = mLinearLayoutManager

        mRecyclerView.apply {

            setHasFixedSize(true)
            layoutManager = mViewManager
        }

        mDetailViewModel.mListTrack.observe(this@DetailFragment, Observer { theTracks ->

            mDotProgressBar.visibility = View.GONE
            if (theTracks != null) {
                mTracksListAdapter =
                    ListTracksAdapter(
                        tracks = theTracks,
                        customOnItemClick = this@DetailFragment
                    )
                mViewAdapter = mTracksListAdapter
                mRecyclerView.adapter = mViewAdapter
            }
        })


        mScrollView.viewTreeObserver.addOnScrollChangedListener(
            ScrollPositionObserver(
                mDetailFragmentDetailBinding.detailImageAlbum,
                mScrollView,
                context!!
            )
        )


        return mDetailFragmentDetailBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailViewModel.mListTrack.value = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.detail_more_icon -> {
                mDetailViewModel.mIsAlbumDuration = true
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(fragmentManager!!.beginTransaction(), bottomSheetFragment.tag)
            }
        }
    }

    /**
     * @param view
     * @param position
     * @param url
     * @param varObject
     *
     * Get event from recycler view on click item
     */
    override fun <T> onItemClick(view: View?, position: Int?, url: String?, varObject: T?) {
        when (view?.id) {
            R.id.detail_more_icon -> {

                mDetailViewModel.mIsAlbumDuration = false
                mDetailViewModel.mTrack.value = varObject as? Track
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(fragmentManager!!.beginTransaction(), bottomSheetFragment.tag)
            }
            else -> {

                // PUT IN SERVICE AND NOT FRAGMENT WITH NOTIFICATION SERVICE
                val track = varObject as? Track

                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                }

                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = MediaPlayer()
                }

                mediaPlayer?.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(activity!!, track?.preview!!.toUri())
                    prepare()
                    start()
                }
            }
        }
    }
}

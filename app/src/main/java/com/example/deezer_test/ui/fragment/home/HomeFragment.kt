package com.example.deezer_test.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.deezer_test.R
import com.example.deezer_test.databinding.FragmentHomeBinding
import com.example.deezer_test.listener.CustomOnItemClickListener
import com.example.deezer_test.model.Album
import com.example.deezer_test.ui.adapter.GridImageAdapter
import com.example.deezer_test.ui.base.BaseFragment
import com.example.deezer_test.utils.SpacesItemDecoration

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class HomeFragment : BaseFragment(), CustomOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val NUM_GRID_COLUMNS = 2

    private val ARGS_ALBUM = "album"

    private val SPAN_COUNT = 2

    private val SPACING = 2

    private val INCLUDE_EDGE = false

    private lateinit var mHomeViewModel: HomeViewModel

    private lateinit var mGridView: RecyclerView

    private lateinit var mViewManager: RecyclerView.LayoutManager

    private lateinit var mGridLayoutManager: GridLayoutManager

    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private lateinit var mAdapter: GridImageAdapter

    private lateinit var mFragementHomeBinding: FragmentHomeBinding

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private var mVisibleItemCount: Int = 0

    private var mTotalItemCount: Int = 0

    private var mTotalItemCountRequest: Int = 0

    private var mFirstVisibleItem: Int = 0

    private var isScrolling: Boolean = false

    private var mNoMoreLoad: Boolean = false

    private var mView: View? = null

    private var mListAlbum: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { theActivity ->
            mHomeViewModel = ViewModelProviders.of(this, mViewModeFactory)[HomeViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView?.let {
            return mView
        } ?: kotlin.run {

            mFragementHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
            mView = mFragementHomeBinding.root

            mFragementHomeBinding.swipeRefreshLayout?.let { theRefresh ->
                mSwipeRefreshLayout = theRefresh
                mSwipeRefreshLayout.setOnRefreshListener(this)
            }

            mGridView = mView!!.findViewById(R.id.recyclerview)
            if (::mHomeViewModel.isInitialized) {

                mFragementHomeBinding.homeViewModel = mHomeViewModel

                mFragementHomeBinding.lifecycleOwner = this@HomeFragment

                setupGridView()
            }
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            mListAlbum = savedInstanceState.getSerializable(ARGS_ALBUM) as? Album
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var album = Album()
        album.listAlbum = mHomeViewModel.mListAlbumCopy
        outState.putSerializable(ARGS_ALBUM, album)
    }

    override fun onRefresh() {
        mAdapter.refreshList()
        mHomeViewModel.mListAlbumCopy.clear()
        mHomeViewModel.getAlbumsRequest(0)
    }

    override fun <T> onItemClick(view: View?, position: Int?, url: String?, varObject: T?) {

        val album = varObject as Album

        val transition = FragmentNavigator.Extras.Builder()
        transition.addSharedElement(view!!, album.id.toString())

        val action: HomeFragmentDirections.ActionHomeFragmentToDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(album)

        Navigation.findNavController(mView!!).navigate(action)
    }


    private fun setupGridView() {

        mGridView.apply {

            val gridWitdh = resources.displayMetrics.widthPixels

            val imageWidth = gridWitdh / NUM_GRID_COLUMNS

            val dividerItemDecoration = SpacesItemDecoration(SPAN_COUNT, SPACING, INCLUDE_EDGE)

            setHasFixedSize(true)

            mGridLayoutManager = GridLayoutManager(context, SPAN_COUNT)

            mGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (mGridView.adapter?.getItemViewType(position)) {
                        GridImageAdapter.AlbumHolder -> 1
                        GridImageAdapter.TitleHolder -> SPAN_COUNT
                        else -> 1
                    }
                }
            }

            mViewManager = mGridLayoutManager

            layoutManager = mViewManager

            addItemDecoration(dividerItemDecoration)

            activity?.also { theActivity ->
                mHomeViewModel.mListAlbum.observe(this@HomeFragment, Observer { theAlbumList ->

                    setItemViewCacheSize(mHomeViewModel.mListAlbumCopy.size)
                    if (mSwipeRefreshLayout.isRefreshing) {
                        mSwipeRefreshLayout.isRefreshing = false
                        val album = theAlbumList as? MutableList<Album>
                        album?.add(0, Album())
                    }

                    if (!::mAdapter.isInitialized) {
                        if (mListAlbum == null) {
                            val album = theAlbumList as? MutableList<Album>
                            album?.add(0, Album())
                            mAdapter = GridImageAdapter(
                                albums = album,
                                mSizeImge = imageWidth,
                                context = context,
                                customOnItemClick = this@HomeFragment
                            )
                            viewAdapter = mAdapter
                            mGridView.adapter = viewAdapter
                            mTotalItemCountRequest = theAlbumList.size
                        } else {
                            mAdapter = GridImageAdapter(
                                albums = mListAlbum?.listAlbum as MutableList<Album>,
                                mSizeImge = imageWidth,
                                context = context,
                                customOnItemClick = this@HomeFragment
                            )
                            viewAdapter = mAdapter
                            mGridView.adapter = viewAdapter
                            mTotalItemCountRequest = theAlbumList.size
                        }
                    } else {
                        if (theAlbumList.isNotEmpty()) {
                            mTotalItemCountRequest = theAlbumList.size
                            mAdapter.updateList(theAlbumList)
                        } else {
                            mNoMoreLoad = true
                        }
                    }

                })
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    mVisibleItemCount = childCount
                    mTotalItemCount = layoutManager!!.itemCount
                    mFirstVisibleItem =
                        (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()

                    if (!mNoMoreLoad && isScrolling && mTotalItemCount <= (mFirstVisibleItem + mVisibleItemCount)) {
                        isScrolling = false
                        mHomeViewModel.getAlbumsRequest(mTotalItemCount)
                    }
                }
            })
        }
    }

}

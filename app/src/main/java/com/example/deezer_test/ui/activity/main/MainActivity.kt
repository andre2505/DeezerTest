package com.example.deezer_test.ui.activity.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deezer_test.R
import com.example.deezer_test.ui.base.BaseActivity

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    private lateinit var mLabelFragment: String

    private lateinit var mToolbar: Toolbar

    private lateinit var mNavigationController: NavController

    private val NAVIGATION_FRAGMENT_HOME: String = "fragment_home"

    private val NAVIGATION_FRAGMENT_DETAIL: String = "fragment_detail"

    private val mReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                //Display the artist in the action bar
                RECEIVER_ARTIST -> {
                    val artistName: TextView = mToolbar.findViewById(R.id.toolbar_artist_name)
                    val artistImg: ImageView = mToolbar.findViewById(R.id.toolbar_artist_image)

                    artistName.text = intent.getStringExtra(EXTRA_NAME)

                    Glide
                        .with(context!!)
                        .load(intent.getStringExtra(EXTRA_IMAGE))
                        .apply(
                            RequestOptions
                                .circleCropTransform()
                        )
                        .into(artistImg)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Single Activity with navigation from architecture components
        mNavigationController = Navigation.findNavController(this, R.id.navHost)

        mToolbar = findViewById(R.id.toolbar_home)
        mNavigationController.addOnDestinationChangedListener(this)

        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (mLabelFragment == NAVIGATION_FRAGMENT_HOME) {
            supportActionBar?.hide()
        }

        LocalBroadcastManager.getInstance(this@MainActivity).registerReceiver(mReceiver, IntentFilter(RECEIVER_ARTIST))
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this@MainActivity).unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

        mLabelFragment = destination.label.toString()

        when (mLabelFragment) {
            NAVIGATION_FRAGMENT_HOME -> {
                supportActionBar?.hide()
            }
            NAVIGATION_FRAGMENT_DETAIL -> {
                supportActionBar?.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        mNavigationController.navigateUp()
        return true
    }

    companion object {
        const val RECEIVER_ARTIST = "receiver_artist"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_NAME = "extra_name"
    }
}

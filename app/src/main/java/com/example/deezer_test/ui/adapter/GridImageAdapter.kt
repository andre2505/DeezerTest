package com.example.deezer_test.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.deezer_test.R
import com.example.deezer_test.listener.CustomOnItemClickListener
import com.example.deezer_test.model.Album


/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class GridImageAdapter(
    var albums: MutableList<Album>? = null,
    private var mSizeImge: Int? = null,
    private val context: Context? = null,
    private val customOnItemClick: CustomOnItemClickListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val AlbumHolder = 1

        val TitleHolder = 2
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.gridImageView)
    }

    class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val progressBar: ProgressBar = itemView.findViewById(R.id.view_t)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var holder: RecyclerView.ViewHolder? = null

        if (viewType == AlbumHolder) {
            holder = ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.grid_view,
                    parent, false
                )
            )
            return holder
        } else if (viewType == TitleHolder) {
            holder = ProgressBarViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_title_list,
                    parent, false
                )
            )
            return holder
        }
        return holder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {

            /// Using glide to display image from Deezer Api and using animation effect (fadeIn)
            albums?.let { theAlbum ->
                mSizeImge?.let { theImgSize ->
                    if (theAlbum.contains(theAlbum[position])) {

                        Glide
                            .with(this.context!!)
                            .load(theAlbum[position].coverMedium)
                            .apply(
                                RequestOptions()
                                    .override(mSizeImge!!, mSizeImge!!)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.ic_disc)
                            )
                            .transition(DrawableTransitionOptions.withCrossFade(400))
                            .into(holder.image)
                    }
                }

                //Event going to customListener Click
                holder.image.setOnClickListener {
                    theAlbum[position].cover?.let { theImage ->
                        val album: Album

                        album = if (theAlbum[position].alternative != null) {
                            theAlbum[position].alternative!!
                        } else {
                            theAlbum[position]
                        }

                        customOnItemClick?.onItemClick<Any>(view = holder.image, varObject = album)
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return albums!!.size
    }

    /**
     * @param position
     *
     * Fix id
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * @param position
     *
     * Return grid element or title element from recyclerView
     */
    override fun getItemViewType(position: Int): Int {
        return if (albums!![position].id == null) {
            TitleHolder
        } else {
            AlbumHolder
        }
    }

    /**
     * @param albums
     *
     * Update list by adding the next element from Deezer API
     */
    fun updateList(albums: List<Album>?) {
        albums?.let {
            this.albums!!.addAll(albums)
            notifyItemRangeInserted(this.albums!!.size - 1, albums.size)
        }
    }

    /**
     * Refresh list on swipe refresh
     */
    fun refreshList() {
        albums?.clear()
        notifyDataSetChanged()
    }

}
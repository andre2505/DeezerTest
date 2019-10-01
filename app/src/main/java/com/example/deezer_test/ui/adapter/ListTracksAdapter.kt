package com.example.deezer_test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer_test.R
import com.example.deezer_test.databinding.ListTrackBinding
import com.example.deezer_test.listener.CustomOnItemClickListener
import com.example.deezer_test.model.Track

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class ListTracksAdapter(
    private val tracks: List<Track>? = null,
    private val customOnItemClick: CustomOnItemClickListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TracksViewHolder constructor(listTrackBinding: ListTrackBinding) :
        RecyclerView.ViewHolder(listTrackBinding.root) {

        val tracksViewBinding: ListTrackBinding = listTrackBinding

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListTrackBinding>(
            layoutInflater,
            R.layout.list_track,
            parent,
            false
        )
        return TracksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TracksViewHolder) {

            tracks?.let { theTracks ->
                holder.tracksViewBinding.track = theTracks[position]

                holder.itemView.setOnClickListener {
                    customOnItemClick!!.onItemClick(varObject = theTracks[position])
                }

                holder.tracksViewBinding.detailMoreIcon.setOnClickListener { theMoreIcone ->
                    customOnItemClick!!.onItemClick(view = theMoreIcone, varObject = theTracks[position])
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return tracks!!.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
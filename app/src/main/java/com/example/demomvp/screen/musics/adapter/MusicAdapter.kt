package com.example.demomvp.screen.musics.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.OnItemRecyclerOnClickListener
import com.example.demomvp.utils.getSongDuration
import kotlinx.android.synthetic.main.layout_item_music.view.*

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    private val mutableListSong = mutableListOf<Song>()
    private var onItemRecyclerListener: OnItemRecyclerOnClickListener? = null
    private val logger = MusicAdapter::class.java.simpleName

    fun updateData(songList: List<Song>) {
        songList.let {
            this.mutableListSong.clear()
            this.mutableListSong.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(listener : OnItemRecyclerOnClickListener?) {
        onItemRecyclerListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_music,
                parent,
                false
            ),
            onItemRecyclerListener
        )
    }

    override fun getItemCount() = mutableListSong.size

    override fun onBindViewHolder(
        holder: MusicAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bindData(mutableListSong[position])
    }

    inner class MyViewHolder(
        itemView: View,
        private val listener: OnItemRecyclerOnClickListener?
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var song: Song? = null
        private var onItemClick: OnItemRecyclerOnClickListener? = null

        fun bindData(data: Song) {
            song = data
            Log.d(logger, "title: ${data.songTitle}, " +
                               "urlSong: ${data.songURL}, " +
                               "urlImage: ${data.songImageURL}")
            itemView.textViewSongName.text = data.songTitle
            itemView.textViewSongDuration.text =
                getSongDuration(itemView.context, data.songURL)
            itemView.setOnClickListener(this)
            onItemClick = listener
            getImageCircle(data)
        }

        override fun onClick(v: View?) {
            onItemClick?.onRecyclerItemClick(song, mutableListSong)
        }

        private fun getImageCircle(song: Song) {
            Glide.with(itemView.context)
                 .load(song.songImageURL)
                 .into(itemView.imageViewSongItem)
        }
    }
}

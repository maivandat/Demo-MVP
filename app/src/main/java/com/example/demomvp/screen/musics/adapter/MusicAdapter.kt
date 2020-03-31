package com.example.demomvp.screen.musics.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.Constant
import com.example.demomvp.utils.OnItemRecyclerOnClickListener
import com.example.demomvp.utils.getSongDuration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_item_music.view.*

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    private val songList = mutableListOf<Song>()
    private var onItemRecyclerOnClickListener: OnItemRecyclerOnClickListener? = null

    companion object {
        private val LOG = MusicAdapter::class.java.simpleName
    }

    fun updateData(mutableSong: MutableList<Song>) {
        mutableSong.let {
            this.songList.clear()
            this.songList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(listenerOn: OnItemRecyclerOnClickListener) {
        onItemRecyclerOnClickListener = listenerOn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_music, parent,
                false
            ), onItemRecyclerOnClickListener!!
        )
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: MusicAdapter.MyViewHolder, position: Int) {
        holder.bindData(songList[position])
    }

    inner class MyViewHolder(
        itemView: View,
        private val onItemRecyclerOnClickListener: OnItemRecyclerOnClickListener
    ): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var song: Song? = null
        private var onItemClick: OnItemRecyclerOnClickListener? = null

        fun bindData(data: Song) {
            song = data
            Log.d(LOG, "title: ${data.songTitle}, urlSong: ${data.songURL}, urlImage: ${data.songImageURL}")
            itemView.textViewSongName.text = data.songTitle
            itemView.textViewSongDuration.text = getSongDuration(itemView.context, data.songURL)
            itemView.setOnClickListener(this)
            onItemClick = onItemRecyclerOnClickListener
            getImageCircle(data)
        }

        override fun onClick(v: View?) {
            onItemClick?.onRecyclerItemClick(song, songList)
        }

        private fun getImageCircle(song: Song) {
            Glide.with(itemView.context)
                .load(song.songImageURL)
                .into(itemView.imageViewSongItem)
        }
    }
}
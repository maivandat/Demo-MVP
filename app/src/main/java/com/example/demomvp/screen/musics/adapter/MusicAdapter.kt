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

class MusicAdapter: RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    private val songs = mutableListOf<Song>()
    private var mListenerOn: OnItemRecyclerOnClickListener? = null

    fun updateData(songs: MutableList<Song>) {
        songs?.let {
            this.songs.clear()
            this.songs.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun registerItemRecyclerViewClickListener(onItemRecyclerOnClickListener: OnItemRecyclerOnClickListener) {
        mListenerOn = onItemRecyclerOnClickListener
    }

    fun setOnItemClickListener(listenerOn: OnItemRecyclerOnClickListener) {
        mListenerOn = listenerOn
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_music, parent,
                false
            ), mListenerOn!!
        )
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: MusicAdapter.MyViewHolder, position: Int) {
        holder.bindData(songs[position])
    }

    inner class MyViewHolder(
        itemView: View,
        private val mListenerOn: OnItemRecyclerOnClickListener

    ): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mSong: Song? = null
        private var listener: OnItemRecyclerOnClickListener? = null

        fun bindData(data: Song) {
            mSong = data
            Log.d(LOG, "title: ${data.title}, urlSong: ${data.urlSong}, urlImage: ${data.urlImage}")
            itemView.textViewSongName.text = data.title
            itemView.textViewSongDuration.text = getSongDuration(itemView.context, data.urlSong)
            itemView.setOnClickListener(this)
            listener = mListenerOn
            getImageCircle(data)
        }

        override fun onClick(v: View?) {
            listener?.onRecyclerItemClick(itemView.context, mSong, songs)
        }

        private fun getImageCircle(song: Song) {
            Glide.with(itemView.context)
                .load(song.urlImage)
                .into(itemView.imageViewSongItem)
        }

    }



    companion object {
        private val LOG = MusicAdapter::class.java.simpleName

    }


}
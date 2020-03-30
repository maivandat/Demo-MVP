package com.example.demomvp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.ItemRecyclerOnClickListener
import com.example.demomvp.utils.getSongDuration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_item_music.view.*

class MusicAdapter(
    private val context: Context,
    private val list: MutableList<Song>
): RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    private val LOG = MusicAdapter::class.java.simpleName
    private var mListener: ItemRecyclerOnClickListener? = null

    fun setOnItemClickListener(listener: ItemRecyclerOnClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_music, parent,
                false
            ), mListener!!
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MusicAdapter.MyViewHolder, position: Int) {
        holder.bindData(list[position])
    }


    inner class MyViewHolder(
        itemView: View,
        private val mListener: ItemRecyclerOnClickListener

    ): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mSong: Song? = null

        fun bindData(data: Song) {
            mSong = data
            Log.d(LOG, "title: ${data.titile}, urlSong: ${data.urlSong}, urlImage: ${data.urlImage}")
            Picasso.with(context).load(data.urlImage).into(itemView.imageViewSongItem)

            itemView.textViewSongName.text = data.titile

            itemView.textViewSongDuration.text = getSongDuration(context, data.urlSong)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mListener?.onRecyclerItemClick(mSong, adapterPosition, list)
        }


    }


}
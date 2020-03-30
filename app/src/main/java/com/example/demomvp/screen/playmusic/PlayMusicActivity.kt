package com.example.demomvp.screen.playmusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.media.MediaPlayerManager
import com.example.demomvp.media.OnClickItemMusic
import com.example.demomvp.utils.getSongDuration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusicActivity : AppCompatActivity(), PlayMusicContract.View,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener,
    SeekBar.OnSeekBarChangeListener, OnClickItemMusic{

    private lateinit var mPlayMusicPresenter: PlayMusicPresenter
    private lateinit var mediaPlayerManager: MediaPlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        initView()
        initData()

    }
    private fun initView() {
        checkBoxPlaySong.setOnCheckedChangeListener(this)
        imageButtonMove.setOnClickListener(this)
        imageButtonNext.setOnClickListener(this)
        imageButtonPrevious.setOnClickListener(this)
        progressSongTime.setOnSeekBarChangeListener(this)
    }

    private fun initData() {
        mPlayMusicPresenter = PlayMusicPresenter()
        mediaPlayerManager = MediaPlayerManager.getInstance(this)
        mPlayMusicPresenter.setView(this)


    }

    override fun getMusicData(song: Song, songs: List<Song>) {
        mPlayMusicPresenter.create(mediaPlayerManager, song, songs)
        mPlayMusicPresenter.startSong(mediaPlayerManager)
        bindData(song)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            mPlayMusicPresenter.pauseSong(mediaPlayerManager)
        }else {
            mPlayMusicPresenter.startSong(mediaPlayerManager)
        }
    }

    private fun bindData(song: Song) {
        textViewSongName.text = song.title.split("-")[0].trim()
        textViewSinger.text = song.title.split("-")[1].trim()
        getImageCircle(song)
    }

    private fun getImageCircle(song: Song) {
        Glide.with(this)
            .load(song.urlImage)
            .into(imageViewPlaySong)
    }

    override fun onClick(v: View?) {
        when(v) {
            imageButtonNext -> {
                mPlayMusicPresenter.nextSong(mediaPlayerManager)
            }

            imageButtonPrevious -> {
                mPlayMusicPresenter.previousSong(mediaPlayerManager)
            }

            imageButtonMove -> {
                mediaPlayerManager.stop()
                finish()
                overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
            }
        }
    }


    override fun sendSong(song: Song) {
        bindData(song)
    }

    override fun updateTime(currentDuration: Int, sCurrentDuration: String) {
        progressSongTime.progress = currentDuration
        textViewTimeBegin.text = sCurrentDuration
    }

    override fun getDuration(duration: Int, sDuration: String) {
        progressSongTime.max = duration
        textViewTimeFinish.text = sDuration
    }

    override fun setState(state: Boolean) {
        checkBoxPlaySong.isChecked = state
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        textViewTimeBegin.text = getSongDuration(progress.toLong())

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        mPlayMusicPresenter.seekSong(mediaPlayerManager, seekBar!!.progress)
    }

    override fun onResume() {
        super.onResume()
        mPlayMusicPresenter.getSongData(this)
    }


    companion object {
        private val LOG = PlayMusicActivity::class.java.simpleName
    }





}

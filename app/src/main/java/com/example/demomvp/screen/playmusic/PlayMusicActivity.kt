package com.example.demomvp.screen.playmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.media.MediaPlayerManager
import com.example.demomvp.media.OnClickItemMusic
import com.example.demomvp.utils.getSongDuration
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusicActivity :
    AppCompatActivity(), PlayMusicContract.View,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener,
    SeekBar.OnSeekBarChangeListener, OnClickItemMusic {
    private lateinit var playMusicPresenter: PlayMusicPresenter
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
        playMusicPresenter = PlayMusicPresenter()
        mediaPlayerManager = MediaPlayerManager.instance
        playMusicPresenter.setView(this)
        mediaPlayerManager.registerItemClickMusic(this)

    }

    override fun getMusicData(song: Song, mutableListSong: List<Song>) {
        playMusicPresenter.create(mediaPlayerManager, song, mutableListSong)
        playMusicPresenter.startSong(mediaPlayerManager)
        bindData(song)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?,
                                  isChecked: Boolean) {
        if (isChecked) {
            playMusicPresenter.pauseSong(mediaPlayerManager)
        }else {
            playMusicPresenter.startSong(mediaPlayerManager)
        }
    }

    private fun bindData(song: Song) {
        textViewSongName.text = song.songTitle.split("-")[0].trim()
        textViewSinger.text = song.songTitle.split("-")[1].trim()
        getImageCircle(song)
    }

    private fun getImageCircle(song: Song) {
        Glide.with(this)
            .load(song.songImageURL)
            .into(imageViewPlaySong)
    }

    override fun onClick(v: View?) {
        when(v) {
            imageButtonNext -> {
                playMusicPresenter.nextSong(mediaPlayerManager)
            }
            imageButtonPrevious -> {
                playMusicPresenter.previousSong(mediaPlayerManager)
            }
            imageButtonMove -> {
                playMusicPresenter.stopSong(mediaPlayerManager)
                finish()
                overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
            }
        }
    }

    override fun sendSong(song: Song) {
        bindData(song)
    }

    override fun updateTime(currentDuration: Int,
                            stringCurrentDuration: String) {
        progressSongTime.progress = currentDuration
        textViewTimeBegin.text = stringCurrentDuration
    }

    override fun getDuration(duration: Int, stringDuration: String) {
        progressSongTime.max = duration
        textViewTimeFinish.text = stringDuration
    }

    override fun setState(state: Boolean) {
        checkBoxPlaySong.isChecked = state
    }

    override fun onProgressChanged(seekBar: SeekBar?,
                                   progress: Int,
                                   fromUser: Boolean) {
        textViewTimeBegin.text = getSongDuration(progress.toLong())

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        playMusicPresenter.seekSong(mediaPlayerManager, seekBar!!.progress)
    }

    override fun onResume() {
        super.onResume()
        playMusicPresenter.getSongData(this)
    }
}

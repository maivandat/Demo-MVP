package com.example.demomvp.ui.playmusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.example.demomvp.R
import com.example.demomvp.model.Song
import com.example.demomvp.utils.getSongDuration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusicActivity : AppCompatActivity(), PlayMusicContract.View,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private lateinit var mPlayMusicPresenter: PlayMusicPresenter
    private var mSong: Song? = null
    private var listSong: MutableList<Song>? = null
    private var mPosition: Int = 0
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var handler: Handler = Handler()
    private var iCurrentPosition: Int = 0
    private var sCurrentPosition: String? = null
    private var updateSeeBarThread = UpdateSeeBarThread()
    private var bState = false

    private val LOG = PlayMusicActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)

        mPlayMusicPresenter = PlayMusicPresenter.getInstance(this)
        mPlayMusicPresenter.setView(this)

        mPlayMusicPresenter.getSongData(this)

        mPlayMusicPresenter.startSong(mSong!!.songURL, mediaPlayer)


        handler.postDelayed(updateSeeBarThread,50)

        checkBoxPlaySong.setOnCheckedChangeListener(this)
        imageButtonMove.setOnClickListener(this)
        imageButtonNext.setOnClickListener(this)
        imageButtonPrevious.setOnClickListener(this)
        progressSongTime.setOnSeekBarChangeListener(this)

    }

    override fun getSong(song: Song, list: MutableList<Song>, position: Int) {
        listSong = list
        mSong = song
        mPosition = position
        bindData(song)
    }

    override fun getSong(song: Song, position: Int) {
        Log.d(LOG, "Position: $position")
        mPosition = position
        bindData(song)
    }


    override fun getDurationSong(position: Long) {
        textViewTimeFinish.text = getSongDuration(position)
        progressSongTime.max = position.toInt()
    }

    override fun getCurrentPosition(iPosition: Int, sPosition: String) {
        iCurrentPosition = iPosition
        sCurrentPosition = sPosition
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            handler.removeCallbacks(updateSeeBarThread)
            mPlayMusicPresenter.pauseSong(mediaPlayer)
            bState = true
        }else {
            handler.postDelayed(updateSeeBarThread, 50)
            mPlayMusicPresenter.playSong(mediaPlayer)
            bState = false
        }
    }

    private fun bindData(song: Song) {
        textViewSongName.text = song.nameSong.split("-")[0].trim()
        textViewSinger.text = song.nameSong.split("-")[1].trim()
        Picasso.with(this).load(song.imageURL).into(imageViewPlaySong)
    }

    override fun onClick(v: View?) {
        when(v) {
            imageButtonNext -> {
                handler.removeCallbacks(updateSeeBarThread)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer()
                checkBoxPlaySong.isChecked = false
                mPlayMusicPresenter.nextSong(listSong!!, mediaPlayer, mPosition)
                handler.postDelayed(updateSeeBarThread, 50)
            }

            imageButtonPrevious -> {
                handler.removeCallbacks(updateSeeBarThread)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer()
                checkBoxPlaySong.isChecked = false
                mPlayMusicPresenter.previousSong(listSong!!, mediaPlayer, mPosition)
                handler.postDelayed(updateSeeBarThread, 50)
            }

            imageButtonMove -> {
                finish()
                overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
            }
        }
    }

    inner class UpdateSeeBarThread: Runnable {
        override fun run() {
            if (textViewTimeBegin.text.equals(textViewTimeFinish.text) && !textViewTimeBegin.equals("00:00")) {
                progressSongTime.progress = progressSongTime.max
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer()
            }
            mPlayMusicPresenter.timeSong(
                mediaPlayer, mPosition,
                listSong!!, progressSongTime.progress,
                progressSongTime.max
            )

            textViewTimeBegin.text = sCurrentPosition
            progressSongTime.progress = iCurrentPosition


            handler.postDelayed(this, 50)
        }

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        textViewTimeBegin.text = getSongDuration(progress.toLong())
        handler.removeCallbacks(updateSeeBarThread)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

        if (bState) {
            mPlayMusicPresenter.playSong(mediaPlayer)
            mediaPlayer.seekTo(seekBar!!.progress)
            handler.postDelayed(updateSeeBarThread, 50)
            checkBoxPlaySong.isChecked = false
        }else {
            mediaPlayer.seekTo(seekBar!!.progress)
            handler.postDelayed(updateSeeBarThread, 50)
        }
    }


}

package com.example.demomvp.screen.musics

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import com.example.demomvp.screen.playmusic.PlayMusicActivity
import com.example.demomvp.utils.Constant
import java.io.Serializable
import java.lang.Exception

class MusicsPresenter internal constructor(
    private val activity: Activity,
    private val musicRepository: MusicRepository
): MusicsContract.Presenter {

    private var mView: MusicsContract.View? = null

    override fun getMusicList() {
        musicRepository.getData(object : OnFetchDataJsonListener<Song> {
            override fun onSuccess(data: MutableList<Song>?) {
                mView!!.musics(data!!)
            }

            override fun onError(e: Exception?) {
                mView!!.onError(e!!)
            }

        })
    }

    override fun sendMusicData(context: Context, obj: Song, songs: List<Song>) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra(Constant.KEY_SONG, obj)
        intent.putExtra(Constant.KEY_LIST_SONG, songs as Serializable)
        context.startActivity(intent)
        activity.overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun setView(view: MusicsContract.View) {
        mView = view
    }

    companion object {

    }

}
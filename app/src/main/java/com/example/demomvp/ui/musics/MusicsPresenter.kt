package com.example.demomvp.ui.musics

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.demomvp.R
import com.example.demomvp.data.data
import com.example.demomvp.model.Song
import com.example.demomvp.ui.playmusic.PlayMusicActivity
import java.io.Serializable

class MusicsPresenter private constructor(private val activity: Activity): MusicsContract.Presenter {

    private var mView: MusicsContract.View? = null

    override fun getMusicList() {
        mView!!.musics(data)
    }

    override fun sendMusicData(context: Context, obj: Song, position: Int, list: MutableList<Song>) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra("song", obj)
        intent.putExtra("position", position)
        intent.putExtra("listSong", list as Serializable)
        context.startActivity(intent)
        activity.overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
    }

    override fun setView(view: MusicsContract.View) {
        mView = view
    }

    companion object {
        private var sInstance: MusicsPresenter? = null

        fun getInstance(activity: Activity): MusicsPresenter {
            if (sInstance == null) {
                sInstance = MusicsPresenter(activity)
            }
            return sInstance!!
        }
    }


}
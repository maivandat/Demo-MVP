package com.example.demomvp.ui.musics

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import com.example.demomvp.data.source.remote.fetchjson.ParseDataWithJson
import com.example.demomvp.ui.playmusic.PlayMusicActivity
import java.io.Serializable
import java.lang.Exception

class MusicsPresenter(
    private val activity: Activity,
    private val mMusicRepositoy: MusicRepository
): MusicsContract.Presenter {

    private var mView: MusicsContract.View? = null

    override fun getMusicList() {
        mMusicRepositoy.getData(object : OnFetchDataJsonListener<Song> {
            override fun onSuccess(data: MutableList<Song>?) {
                mView!!.musics(data!!)
            }

            override fun onError(e: Exception?) {

            }

        })
    }

    override fun sendMusicData(context: Context, obj: Song, position: Int, list: MutableList<Song>) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra("song", obj)
        intent.putExtra("position", position)
        intent.putExtra("listSong", list as Serializable)
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
        private var sInstance: MusicsPresenter? = null

        fun getInstance(activity: Activity, mMusicRepositoy: MusicRepository): MusicsPresenter {
            if (sInstance == null) {
                sInstance = MusicsPresenter(activity, mMusicRepositoy)
            }
            return sInstance!!
        }
    }


}
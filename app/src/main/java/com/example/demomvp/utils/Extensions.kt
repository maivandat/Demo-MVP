package com.example.demomvp.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*


fun getMediaPlayer(context: Context?): MediaPlayer? {
    val mediaplayer = MediaPlayer()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        return mediaplayer
    }
    val any = try {
        val cMediaTimeProvider =
            Class.forName("android.media.MediaTimeProvider")
        val cSubtitleController =
            Class.forName("android.media.SubtitleController")
        val iSubtitleControllerAnchor =
            Class.forName("android.media.SubtitleController\$Anchor")
        val iSubtitleControllerListener =
            Class.forName("android.media.SubtitleController\$Listener")
        val constructor = cSubtitleController.getConstructor(
            *arrayOf(
                Context::class.java, cMediaTimeProvider, iSubtitleControllerListener
            )
        )
        val subtitleInstance: Any = constructor.newInstance(context, null, null)
        val f: Field = cSubtitleController.getDeclaredField("mHandler")
        f.setAccessible(true)
        try {
            f.set(subtitleInstance, Handler())
        } catch (e: IllegalAccessException) {
            return mediaplayer
        } finally {
            f.setAccessible(false)
        }
        val setsubtitleanchor: Method = mediaplayer.javaClass.getMethod(
            "setSubtitleAnchor",
            cSubtitleController,
            iSubtitleControllerAnchor
        )
        setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null)
        //Log.e("", "subtitle is setted :p");
    } catch (e: Exception) {
    }
    return mediaplayer
}

fun getSongDuration(context: Context?, url: String): String {
    val mediaPlayer = getMediaPlayer(context)
    mediaPlayer!!.setDataSource(url)
    mediaPlayer.prepare()
    return SimpleDateFormat("mm:ss").format(Date(mediaPlayer.duration.toLong()))
}

fun getSongDuration(duration: Long): String {
    return SimpleDateFormat("mm:ss").format(Date(duration))
}
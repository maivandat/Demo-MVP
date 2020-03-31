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
    val mediaPlayer = MediaPlayer()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        return mediaPlayer
    }
    try {
        val mediaTimeProvider =
            Class.forName("android.media.MediaTimeProvider")
        val subTitleController =
            Class.forName("android.media.SubtitleController")
        val subTitleControllerAnchor =
            Class.forName("android.media.SubtitleController\$Anchor")
        val subTitleControllerListener =
            Class.forName("android.media.SubtitleController\$Listener")
        val constructor = subTitleController.getConstructor(
            *arrayOf(
                Context::class.java, mediaTimeProvider, subTitleControllerListener
            )
        )
        val subTitleInstance: Any = constructor.newInstance(context, null, null)
        val f: Field = subTitleController.getDeclaredField("mHandler")
        f.isAccessible = true
        try {
            f.set(subTitleInstance, Handler())
        } catch (e: IllegalAccessException) {
            return mediaPlayer
        } finally {
            f.isAccessible = false
        }
        val setSubtitleAnchor: Method = mediaPlayer.javaClass.getMethod(
            "setSubtitleAnchor",
            subTitleController,
            subTitleControllerAnchor
        )
        setSubtitleAnchor.invoke(mediaPlayer, subTitleInstance, null)
        //Log.e("", "subtitle is setter :p");
    } catch (e: Exception) {
    }
    return mediaPlayer
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
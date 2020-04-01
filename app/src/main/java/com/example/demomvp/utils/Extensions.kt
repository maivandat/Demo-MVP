package com.example.demomvp.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
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
        val subTitleInstance = constructor.newInstance(context, null, null)
        val filed = subTitleController.getDeclaredField("mHandler")
            filed.isAccessible = true
        try {
            filed.set(subTitleInstance, Handler())
        } catch (e: IllegalAccessException) {
            return mediaPlayer
        } finally {
            filed.isAccessible = false
        }
        val subTitleAnchor =
            mediaPlayer.javaClass.getMethod("setSubtitleAnchor",
                                            subTitleController,
                                            subTitleControllerAnchor)
            subTitleAnchor.invoke(mediaPlayer, subTitleInstance, null)
        } catch (e: Exception) {
            /**/
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
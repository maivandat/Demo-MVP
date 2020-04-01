package com.example.demomvp.data.source.remote.fetchjson;

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.model.SongEntry
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ParseDataWithJson {

    @Throws(Exception::class)
    fun getJsonFromUrl(urlString: String?): String? {
        val url = URL(urlString)
        val httpURLConnection =
            url.openConnection() as HttpURLConnection
        httpURLConnection.connectTimeout = TIME_OUT
        httpURLConnection.readTimeout = TIME_OUT
        httpURLConnection.requestMethod = METHOD_GET
        httpURLConnection.doOutput = true
        httpURLConnection.connect()
        val bufferedReader =
            BufferedReader(InputStreamReader(url.openStream()))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        httpURLConnection.disconnect()
        return stringBuilder.toString()
    }

    fun parseJsonToData(jsonObject: JSONObject): List<Song>? {
        val songList: MutableList<Song> = ArrayList()
        try {
            val jsonArray = jsonObject.getJSONArray(SongEntry.SONG)
            for (i in 0 until jsonArray.length()) {
                val songJson = jsonArray.getJSONObject(i)
                val song = parseJsonToObject(songJson)
                songList.add(song)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return songList
    }

    private fun parseJsonToObject(jsonObjectSong: JSONObject): Song {
        var song: Song? = null
        try {
            song = Song(jsonObjectSong.getString(SongEntry.TITLE),
                        jsonObjectSong.getString(SongEntry.URL_IMAGE),
                        jsonObjectSong.getString(SongEntry.URL_SONG))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return song!!
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}

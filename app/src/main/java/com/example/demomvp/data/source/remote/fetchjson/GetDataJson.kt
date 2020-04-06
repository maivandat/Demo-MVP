package com.example.demomvp.data.source.remote.fetchjson

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import com.example.demomvp.utils.Constant

class GetDataJson(private var listener: OnFetchDataJsonListener<Song>) {

    fun getSongs() {
        val url = Constant.BASE_URL
        GetJsonFromUrl(listener).execute(url)
    }

}

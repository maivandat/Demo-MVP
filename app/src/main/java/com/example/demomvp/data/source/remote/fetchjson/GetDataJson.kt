package com.example.demomvp.data.source.remote.fetchjson;

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener;
import com.example.demomvp.utils.Constant;

class GetDataJson(private var onFetchDataJsonListener: OnFetchDataJsonListener<Song>?) {

    fun getSongs() {
        val url = Constant.BASE_URL
        GetJsonFromUrl(onFetchDataJsonListener).execute(url)
    }

}

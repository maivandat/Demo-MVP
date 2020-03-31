package com.example.demomvp.data.source.remote.fetchjson;

import android.os.AsyncTask
import android.util.Log
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import org.json.JSONException
import org.json.JSONObject

class GetJsonFromUrl constructor(
    private val onFetchDataJsonListener: OnFetchDataJsonListener<Song>?
): AsyncTask<String, Void, String>() {

    companion object {
        private val LOG = GetDataJson::class.java.simpleName
    }

    override fun doInBackground(vararg strings: String?): String? {
        var data = ""
        try {
            val parseDataWithJson = ParseDataWithJson()
            data = parseDataWithJson.getJsonFromUrl(strings[0])!!
        } catch (e: Exception) {
            onFetchDataJsonListener!!.onError(e)
        }
        return data
    }

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        Log.d(LOG, data)
        data?.let {
            try {
                val jsonObject = JSONObject(it)
                onFetchDataJsonListener!!.onSuccess(ParseDataWithJson().parseJsonToData(jsonObject))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}

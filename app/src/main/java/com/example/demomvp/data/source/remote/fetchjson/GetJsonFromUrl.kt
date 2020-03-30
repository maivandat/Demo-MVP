package com.example.demomvp.data.source.remote.fetchjson;

import android.os.AsyncTask
import android.util.Log
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import org.json.JSONException
import org.json.JSONObject

class GetJsonFromUrl constructor(
    private val mListener: OnFetchDataJsonListener<Song>?
): AsyncTask<String, Void, String>() {
    private val LOG = GetDataJson::class.java.simpleName
    override fun doInBackground(vararg strings: String?): String? {
        var data = ""
        try {
            val parseDataWithJson = ParseDataWithJson()
            data = parseDataWithJson.getJsonFromUrl(strings[0])!!
        } catch (e: Exception) {
            mListener!!.onError(e)
        }
        return data
    }

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        Log.d(LOG, data)
        if (data != null) {
            try {
                val jsonObject = JSONObject(data)
                mListener!!.onSuccess(ParseDataWithJson().parseJsonToData(jsonObject))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}

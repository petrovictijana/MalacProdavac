package com.example.batmobile.network

import com.android.volley.VolleyError
import org.json.JSONObject

interface ApiInterface {
    fun sendPostRequestWithParams(url:String,
                                  params:       Map<String,String>,
                                  onSuccess:    (String) ->Unit,
                                  onError:      (VolleyError) -> Unit)
    fun sendPostRequestWithJSONObject(url:String,
                                      jsonObject:   JSONObject,
                                      onSuccess:    (JSONObject) ->Unit,
                                      onError:      (VolleyError) -> Unit)
}
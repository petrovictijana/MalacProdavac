package com.example.batmobile.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ApiClient(private val context: Context): ApiInterface {

    private val queue: RequestQueue = Volley.newRequestQueue(context)
    override fun sendPostRequestWithParams(
        url: String,
        params: Map<String, String>,
        onSuccess: (String) -> Unit,
        onError: (VolleyError) -> Unit
    )
    {
        val stringRequest = object: StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                onSuccess(response)
            },
            Response.ErrorListener { error ->
                onError(error)
            })
            {
                override fun getParams(): MutableMap<String, String> {
                    return params.toMutableMap()
                }
            }
        queue.add(stringRequest)
    }

    override fun sendPostRequestWithJSONObject(
        url: String,
        jsonObject: JSONObject,
        onSuccess: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonObject,
            {response -> onSuccess(response)},
            {error -> onError(error)}
        )
        queue.add(jsonObjectRequest)
    }
}
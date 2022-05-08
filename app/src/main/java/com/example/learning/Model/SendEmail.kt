package com.example.learning.Model

import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.learning.Constants.Constants
import com.example.learning.Constants.Constants.Companion.SERVER_KEY
import com.example.learning.Constants.Constants.Companion.auth

import org.json.JSONException
import org.json.JSONObject

class SendEmail {
    companion object {

        fun sendEmail(
            context: Context,
            from: String,
            to: String,
            subject: String,
            text: String
        ) {
            var obj: JSONObject? = null
            try {
                obj = JSONObject()
                obj.put("from", from)
                obj.put("email", to)
                obj.put("subject", subject)
                obj.put("text", text)
                obj.put("auth", auth)
                Log.e("aaa", obj.toString())
            } catch (e: JSONException) {
                Log.e("obj", e.localizedMessage)

                e.printStackTrace()
            }
            val jsObjRequest: JsonObjectRequest =
                object : JsonObjectRequest(
                    Request.Method.POST,
                    "https://us-central1-learning-c0b44.cloudfunctions.net/api/v1/send",
                    obj,
                    Response.Listener { response ->

                        Log.e("True", response.toString() + "") },
                    Response.ErrorListener { error ->

                        Log.e("False", error.toString() + "")
                      }) {
//

                }
            val requestQueue = Volley.newRequestQueue(context)
            val socketTimeout = 1000 * 60 // 60 seconds
            val policy: RetryPolicy = DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            jsObjRequest.retryPolicy = policy
            requestQueue.add(jsObjRequest)
        }

    }
}
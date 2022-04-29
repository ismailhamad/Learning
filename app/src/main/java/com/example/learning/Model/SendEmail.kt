package com.example.learning.Model

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import org.json.JSONException
import org.json.JSONObject

class SendEmail {
    companion object {

        fun sendEmail(
            context: Context,
            from: String,
            to: String,
            subject: String,
            html: String
        ) {
            var obj: JSONObject? = null
            try {
                obj = JSONObject()
                obj.put("from", from)
                obj.put("to", to)
                obj.put("subject", subject)
                obj.put("html", html)
//                Log.e("return here>>", obj.toString())
            } catch (e: JSONException) {
                Log.e("msg_token", e.localizedMessage)

                e.printStackTrace()
            }
            val jsObjRequest: JsonObjectRequest =
                object : JsonObjectRequest(
                    Request.Method.POST,
                    "https://us-central1-learning-c0b44.cloudfunctions.net/sendMailOverHTTP",
                    obj,
                    Response.Listener { response -> Log.e("True", response.toString() + "") },
                    Response.ErrorListener { error -> Log.e("False", error.toString() + "") }) {
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


    fun sendNotificationFirebase(context : Context, title: String, msg: String, topic: String) {
        val SERVER_KEY = "key=AAAALvvMH7c:APA91bExtzjgC1gSjwlV0hmQExQQFDoHTV5SdXMV7l3Pq9arnq6P9TTOniRNNQSfjXh_TfV5zj19Oup4_HGD0T7K8U-vc8-mLGWE1fOom4DB1PljsEIoB41vVTqRNWUPvEqRJPZKsULk"
        var obj: JSONObject? = null
        var objData: JSONObject?
        var dataobjData: JSONObject? = null
        try {
            obj = JSONObject()
            objData = JSONObject()
            objData.put("body", msg)
            objData.put("title", title)
            objData.put("sound", "default")
            objData.put("sound", "/topics/$topic")
//            objData.put("tag", token)
            objData.put("priority", "high")
            dataobjData = JSONObject()
            dataobjData.put("text", msg)
            dataobjData.put("title", title)
//            obj.put("to", token)
            obj.put("to","/topics/$topic")
            obj.put("notification", objData)
            obj.put("data", dataobjData)
            Log.e("return here>>", obj.toString())
        } catch (e: JSONException) {
            Log.e("msg_token" , e.localizedMessage)

            e.printStackTrace()
        }
        val jsObjRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                Response.Listener { response -> Log.e("True", response.toString() + "") },
                Response.ErrorListener { error -> Log.e("False", error.toString() + "") }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = SERVER_KEY
                    params["Content-Type"] = "application/json"
                    return params
                }
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
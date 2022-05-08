package com.example.learning.Constants

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar

class Constants {
    companion object{
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = "AAAAkPtElvI:APA91bGCaFfSjiGyERPqyzYPf85XLPQyKLVg_c0a873C1xN9GDhGpvMrPpXKtNO_KPveamzJ5Riydu0u_ZRXK69XvyHZh6-mHPRLwTvqkz49-j2p2F9xOjcUyDxztX4ccrb_FXXMeeUP" // get firebase server key from firebase project setting
        const val auth = "AIzaSyAA2hae3PgHQMd9UeeLArSOXC9pKUFUFMs"
        const val CONTENT_TYPE = "application/json"
        const val redColor = "#E30425"
        const val greenColor = "#1AD836"

        fun showSnackBar(view:View, title:String, color:String){
            Snackbar.make(view, title, Snackbar.LENGTH_LONG).apply {
                animationMode = ANIMATION_MODE_SLIDE
                setBackgroundTint(Color.parseColor(color))
                setTextColor(Color.parseColor("#FFFFFF"))
                show()
            }// apply
        }// show snackBar
    }




}
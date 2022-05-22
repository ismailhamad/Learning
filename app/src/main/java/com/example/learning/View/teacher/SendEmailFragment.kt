package com.example.learning.View.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learning.Constants.Constants
import com.example.learning.Model.SendEmail
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.View.student.Student
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_send_email.*

class SendEmailFragment : Fragment(R.layout.fragment_send_email) {

val args:SendEmailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val users =  args.userEmail
        imageButton9.setOnClickListener {
            findNavController().navigateUp()
        }
        btn_sendEmail.setOnClickListener {
            if (tv_titleEmail.text.isNotEmpty() && tv_desEmail.text.isNotEmpty()){
                SendEmail.sendEmail(
                    activity as Teacher,"learing@gmail.com",users!!.email,tv_titleEmail.text.toString(),
//                    activity as Teacher,"learing@gmail.com","saleemmater123@gmail.com",tv_titleEmail.text.toString(),
                    tv_desEmail.text.toString()
                )
                Constants.showSnackBar(
                    view,
                    "تم ارسال الإيميل",
                    Constants.greenColor
                )
                tv_titleEmail.text = null
                tv_desEmail.text = null
            }else{
                Constants.showSnackBar(
                    view,
                    "إملا الحقول المطلوبة",
                    Constants.redColor
                )
            }

        }

    }

}
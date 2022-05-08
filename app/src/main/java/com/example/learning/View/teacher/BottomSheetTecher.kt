package com.example.learning.View.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learning.R
import com.example.learning.View.ChatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_techer.*

class bottomSheetTecher : BottomSheetDialogFragment() {
    val args: bottomSheetTecherArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_techer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val users = args.userSheet
        tv_chatUser.setOnClickListener {
            val i = Intent(activity, ChatActivity::class.java)
            i.putExtra("usersT", users)
            startActivity(i)
        }
        tv_sendEmail.setOnClickListener {
            val Bundle = Bundle().apply {
                putSerializable("userEmail", users)
            }
            findNavController().navigate(
                R.id.action_bottomSheetTecher_to_sendEmailFragment,
                Bundle
            )
        }
    }


}
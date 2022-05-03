package com.example.learning.View.teacher

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.AlluserAssi
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_all_user_assigment.*


class AllUserAssigmentFragment : Fragment() {
lateinit var learningViewModel: LearningViewModel
lateinit var alluserAssi: AlluserAssi
val args:AllUserAssigmentFragmentArgs by navArgs()
var pdf:Any?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_user_assigment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val idcourse = args.idcourse
        val idlecture = args.idlecture
        val idAssi  = args.idassi
        learningViewModel.getAlluserAddAssigment(idcourse,idlecture,idAssi)
        setupReceyclview()
        learningViewModel.userassi.observe(viewLifecycleOwner, Observer {
            alluserAssi.differ.submitList(it)
        })

        alluserAssi.setOnItemClickListener {
            var request= DownloadManager.Request(Uri.parse(it.toString()))
                .setTitle("Download")
                .setDescription("Download a file")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm!!.enqueue(request)
        }

    }

    fun setupReceyclview(){
        alluserAssi = AlluserAssi()
        rv_assiUser.apply {
            adapter = alluserAssi
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
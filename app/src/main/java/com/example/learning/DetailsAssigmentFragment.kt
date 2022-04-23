package com.example.learning

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.learning.Model.users
import com.example.learning.View.Student
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_assigment.*
import kotlinx.android.synthetic.main.fragment_details_assigment.*


class DetailsAssigmentFragment : Fragment(R.layout.fragment_details_assigment) {
  lateinit var learningViewModel: LearningViewModel
   val args:DetailsAssigmentFragmentArgs by navArgs()
    var users:users?=null
    var fileUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        val assigment = args.assigment
        val idlecture = args.idlecture
        val idCourse  = args.idcoursee
     //  learningViewModel.getuserAddAssigment(idCourse,idlecture,assigment.id.toString())
        name_assi.text = assigment.name
        dec_assi.text = assigment.description
        name_file_assi.text = assigment.name
        learningViewModel.userADDASS.observe(viewLifecycleOwner, Observer {
            val id = it?.get("id")
            if ( id !=null) {
                submission.text ="Submitted"
                view10.setBackgroundColor(Color.parseColor("#5EC6F392"))
                button_Edit.setOnClickListener{
                    learningViewModel.updateUserAssignment(view,idCourse, idlecture,assigment.id.toString(),fileUri!!,fileUri.toString())
                }
                delete_assii.setOnClickListener {
                    learningViewModel.deleteUserAssignment(view,idCourse,idlecture,assigment.id.toString(),users!!.id.toString())
                }
                fileassi.text = it?.get("file").toString()
                button_adduserAssi.visibility =View.GONE
                button_Edit.visibility =View.VISIBLE
                delete_assii.visibility =View.VISIBLE
            }else{
                view10.setBackgroundColor(Color.parseColor("#5EFFFFFF"))
                delete_assii.visibility =View.GONE
                button_Edit.visibility =View.GONE
                button_adduserAssi.visibility =View.VISIBLE


            }


        })

        button_adduserAssi.setOnClickListener {

            fileUri?.let { it1 ->
                assigment.id?.let { it2 ->
                    users?.let { it3 ->
                        learningViewModel.userAddAssignment(view, it3,idCourse,idlecture, it2,
                            it1,fileUri.toString())
                    }
                }
            }
        }
        imageButton_addfile.setOnClickListener {
            chooseFile()
        }

        downlod_file_assi.setOnClickListener {
            var request= DownloadManager.Request(Uri.parse(assigment.file))
                .setTitle("Download")
                .setDescription("Download a ${assigment.name}")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
            val imageurl: String = assigment.file.toString()

            val dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm!!.enqueue(request)
        }
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for(item in it){
                users = item
            }
        })





    }



    private fun chooseFile() {
        val intent = Intent()
        intent.type = "application/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 3000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            fileassi.append(fileUri.toString())
        }
    }


}
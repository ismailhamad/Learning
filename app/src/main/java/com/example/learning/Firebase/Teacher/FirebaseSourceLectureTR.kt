package com.example.learning.Firebase.Teacher

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.example.learning.Model.lecture
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseSourceLectureTR(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var countUserListprivMutableLiveData: MutableLiveData<Int>

    fun addLecture(
        view: View,
        lecture: lecture,
        uriVideo: Uri?,
        uriFile: Uri?,
        document: String,
        code: String
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        analytics = Firebase.analytics
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        if (code == "5000") {
            storageRef!!.child("video/" + "${lecture.id}").putFile(uriVideo!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.video = uri.toString()
                        db.collection("courses/${document}/lecture/").document(lecture.id!!)
                            .set(lecture.getlectureHashMap())
                        Constants.showSnackBar(
                            view,
                            "تم اضافة المحاضرة",
                            Constants.greenColor
                        )
                    }
                    analytics.logEvent("addLecture") {
                        param("name_lecture", lecture.name!!)
                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل اضافة المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        } else if (code == "4000") {
            storageRef!!.child("files/" + "${lecture.id}").putFile(uriFile!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.file = uri.toString()
                        db.collection("courses/${document}/lecture/").document(lecture.id!!)
                            .set(lecture.getlectureHashMap())
                        Constants.showSnackBar(
                            view,
                            "تم اضافة المحاضرة",
                            Constants.greenColor
                        )
                        analytics.logEvent("addLecture") {
                            param("name_lecture", lecture.name!!)
                        }

                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل اضافة المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        } else {
            storageRef!!.child("files/" + "${lecture.id}").putFile(uriFile!!)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.file = uri.toString()
                        storageRef!!.child("video/" + "${lecture.id}").putFile(uriVideo!!)
                            .addOnSuccessListener { videotaskSnapshot ->
                                progressDialog.dismiss()
                                videotaskSnapshot.storage.downloadUrl.addOnSuccessListener { uriv ->
                                    lecture.video = uriv.toString()
                                    db.collection("courses/${document}/lecture/")
                                        .document(lecture.id!!).set(lecture.getlectureHashMap())
                                    Constants.showSnackBar(
                                        view,
                                        "تم اضافة المحاضرة",
                                        Constants.greenColor
                                    )
                                    analytics.logEvent("addLecture") {
                                        param("name_lecture", lecture.name!!)
                                    }

                                }
                            }

                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل اضافة المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }

        }
    }

    fun updateLecture(
        view: View,
        lecture: lecture,
        uriVideo: Uri?,
        uriFile: Uri?,
        document: String,
        documentLecture: String,
        code: String
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        if (code == "5000") {
            storageRef!!.child("video/" + "${lecture.id}").putFile(uriVideo!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.video = uri.toString()

                        db.collection("courses/${document}/lecture/").document(documentLecture)
                            .update(lecture.getlectureHashMap())
                        Constants.showSnackBar(
                            view,
                            "تم تعديل المحاضرة",
                            Constants.greenColor
                        )
                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل تعديل المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        } else if (code == "4000") {
            storageRef!!.child("files/" + "${lecture.id}").putFile(uriFile!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.file = uri.toString()
                        db.collection("courses/${document}/lecture/").document(documentLecture)
                            .update(lecture.getlectureHashMap())
                        Constants.showSnackBar(
                            view,
                            "تم تعديل المحاضرة",
                            Constants.greenColor
                        )

                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل تعديل المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        } else {
            storageRef!!.child("files/" + "${lecture.id}").putFile(uriFile!!)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        lecture.file = uri.toString()
                        storageRef!!.child("video/" + "${lecture.id}").putFile(uriVideo!!)
                            .addOnSuccessListener { videotaskSnapshot ->
                                progressDialog.dismiss()
                                videotaskSnapshot.storage.downloadUrl.addOnSuccessListener { uriv ->
                                    lecture.video = uriv.toString()
                                    Log.e("aaaa", "lecture.id!! ${lecture.id!!}")
                                    db.collection("courses/${document}/lecture/")
                                        .document(documentLecture)
                                        .update(lecture.getlectureHashMap())
                                    Constants.showSnackBar(
                                        view,
                                        "تم تعديل المحاضرة",
                                        Constants.greenColor
                                    )
                                }
                            }

                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل تعديل المحاضرة",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }

        }


    }

    fun deleteLecture(view: View, document: String, documentLecture: String) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        db = Firebase.firestore
        db.collection("courses/${document}/lecture/").document(documentLecture).get()
            .addOnSuccessListener {
                storageRef!!.child("video/${it.get("id")}").delete()
                storageRef!!.child("files/${it.get("id")}").delete()
                db.collection("courses/${document}/lecture/").document(documentLecture).delete()
                Constants.showSnackBar(
                    view,
                    "تم حذف المحاضرة",
                    Constants.greenColor
                )

            }

    }

    fun seeLecture(view: View, document: String, documentLecture: String) {
        db = Firebase.firestore
        analytics = Firebase.analytics
        db.collection("courses/${document}/lecture/").document(documentLecture).get()
            .addOnSuccessListener {
                if (it.get("seeLecture") == true) {
                    db.collection("courses/${document}/lecture/").document(documentLecture)
                        .update("seeLecture", false)
                    Constants.showSnackBar(
                        view,
                        "تم اخفاء المحاضرة",
                        Constants.greenColor
                    )
                    analytics.logEvent("seeLecture") {
                        param("name_lecture", it.get("name").toString())
                    }
                } else {
                    db.collection("courses/${document}/lecture/").document(documentLecture)
                        .update("seeLecture", true)
                    Constants.showSnackBar(
                        view,
                        "تم اضهار المحاضرة",
                        Constants.greenColor
                    )

                }
            }
    }

    fun getCountUserShowLecture(
        documentCourses: String,
        documentLecture: String,
    ): MutableLiveData<Int> {
        db = Firebase.firestore
        var count = 0
        countUserListprivMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users").get()
            .addOnSuccessListener {
                for (i in it) {
                    countUserListprivMutableLiveData.postValue(count++)
                }
            }

        return countUserListprivMutableLiveData

    }

}
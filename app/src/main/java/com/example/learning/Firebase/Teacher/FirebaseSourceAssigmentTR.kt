package com.example.learning.Firebase.Teacher

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.Assignment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseSourceAssigmentTR(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var usersAddAssiListMutableLiveData: MutableLiveData<HashMap<String, Any?>>
    lateinit var usersAllAddAssiListMutableLiveData: MutableLiveData<List<HashMap<String, Any?>>>
    lateinit var countUserAddAssigmentListprivMutableLiveData: MutableLiveData<Int>

    fun addAssignment(
        view: View,
        assignment: Assignment,
        documentCourses: String,
        documentLecture: String,
        file: Uri
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        analytics = Firebase.analytics
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        storageRef!!.child("assignment/" + "${assignment.id}").putFile(file!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    assignment.file = uri.toString()
                    db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment")
                        .document(assignment.id!!)
                        .set(assignment.getAssignmentHashMap())
                    Constants.showSnackBar(
                        view,
                        "تم اضافة الواجب",
                        Constants.greenColor
                    )
                    analytics.logEvent("addAssignment") {
                        param("name_Assignment", assignment.name.toString())
                    }
                }
            }.addOnFailureListener { exception ->
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل اضافة الواجب",
                    Constants.redColor
                )
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun updateAssignment(
        view: View,
        assignment: Assignment,
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String,
        file: Uri
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        storageRef!!.child("assignment/" + "${assignment.id}").putFile(file!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    assignment.file = uri.toString()
                    db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment")
                        .document(documentAssignment)
                        .update(assignment.getAssignmentHashMap())
                    Constants.showSnackBar(
                        view,
                        "تم تعديل الواجب",
                        Constants.greenColor
                    )
                }
            }.addOnFailureListener { exception ->
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تعديل الواجب",
                    Constants.redColor
                )
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun deleteAssignment(
        view: View,
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        db = Firebase.firestore
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment")
            .document(documentAssignment).get()
            .addOnSuccessListener {
                storageRef!!.child("assignment/${it.get("id")}").delete()
                db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment")
                    .document(documentAssignment).delete()
                Constants.showSnackBar(
                    view,
                    "تم حذف الواجب",
                    Constants.greenColor
                )

            }.addOnFailureListener {
                Constants.showSnackBar(
                    view,
                    "فشل حذف الواجب",
                    Constants.redColor
                )
            }

    }

    fun getuserAddAssigment(
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String
    ): MutableLiveData<HashMap<String, Any?>> {
        db = Firebase.firestore
        auth = Firebase.auth
        usersAddAssiListMutableLiveData = MutableLiveData()
        auth.currentUser!!.uid.let {
            db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
                .document(it).addSnapshotListener { value, error ->
                    val idusers = value?.get("id")
                    val file = value?.get("file")
                    val name = value?.get("name")
                    val data = hashMapOf<String, Any?>(
                        "id" to idusers,
                        "file" to file,
                        "name" to name
                    )
                    usersAddAssiListMutableLiveData?.postValue(data)
                }

        }

        return usersAddAssiListMutableLiveData

    }

    fun getAlluserAddAssigment(
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String
    ): MutableLiveData<List<HashMap<String, Any?>>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val arrayList = arrayListOf<HashMap<String, Any?>>()
        usersAllAddAssiListMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    val idusers = i?.get("id")
                    val file = i?.get("file")
                    val name = i?.get("name")
                    val data = hashMapOf<String, Any?>(
                        "id" to idusers,
                        "file" to file,
                        "name" to name
                    )
                    arrayList.add(data)

                    usersAllAddAssiListMutableLiveData?.postValue(arrayList)
                }

            }
        return usersAllAddAssiListMutableLiveData

    }

    fun getCountUserAddAssigment(
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String
    ): MutableLiveData<Int> {
        db = Firebase.firestore
        auth = Firebase.auth
        countUserAddAssigmentListprivMutableLiveData = MutableLiveData()
        var count = 0
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
            .get().addOnSuccessListener {
                for (i in it) {
                    Log.e("aa", "name ddd ${i.get("id")}")
                    countUserAddAssigmentListprivMutableLiveData.postValue(++count)
                }
            }

        return countUserAddAssigmentListprivMutableLiveData

    }
}
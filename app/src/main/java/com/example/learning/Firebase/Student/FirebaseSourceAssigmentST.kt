package com.example.learning.Firebase.Student

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.Assignment
import com.example.learning.Model.users
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseSourceAssigmentST(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null

    lateinit var AssignmentListMutableLiveData: MutableLiveData<List<Assignment>>

    fun getAssignment(
        documentCourses: String,
        documentLecture: String,
    ): MutableLiveData<List<Assignment>> {
        db = Firebase.firestore
        AssignmentListMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment")
            .addSnapshotListener { result, error ->
                val assignment = result!!.toObjects<Assignment>()

                AssignmentListMutableLiveData.postValue(assignment)
            }
        return AssignmentListMutableLiveData

    }
    fun userAddAssignment(view: View, users: users, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri,
                          fileString: String
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        analytics = Firebase.analytics
        var data = hashMapOf<String, Any>(
            "id" to users.id!!,
            "name" to users.name!!,
            "lastName" to users.lastName!!,
            "email" to users.email!!,
            "file" to fileString
        )
        storageRef!!.child("assignment/" + "${users.id}").putFile(file!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    data["file"] = uri.toString()
                    db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
                        .document(users.id!!)
                        .set(data)

                    Constants.showSnackBar(
                        view,
                        "تم تسليم الواجب",
                        Constants.greenColor
                    )
                    analytics.logEvent("userAddAssignment") {
                        param("name_User", users.name)
                    }
                }
            }.addOnFailureListener { exception ->
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تسليم الواجب",
                    Constants.redColor
                )
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun updateUserAssignment(
        view: View,
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String,
        file: Uri,
        fileString: String
    ) {
        storge = Firebase.storage
        auth = Firebase.auth
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        var data = hashMapOf<String, Any>(
            "file" to fileString
        )
        storageRef!!.child("assignment/" + "${auth.currentUser!!.uid}").putFile(file!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    data["file"] = uri.toString()
                    db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
                        .document(auth.currentUser!!.uid)
                        .update(data)

                    Constants.showSnackBar(
                        view,
                        "تم تعديل  تسليم الواجب",
                        Constants.greenColor
                    )
                }
            }.addOnFailureListener { exception ->
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تعديل تسليم الواجب",
                    Constants.redColor
                )
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun deleteUserAssignment(
        view: View,
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String,
        documentUserAssignment: String
    ) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        db = Firebase.firestore
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
            .document(documentUserAssignment).get()
            .addOnSuccessListener {
                storageRef!!.child("assignment/${it.get("id")}").delete()
                db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment")
                    .document(documentUserAssignment).delete()
                Constants.showSnackBar(
                    view,
                    "تم حذف التسليم",
                    Constants.greenColor
                )

            }.addOnFailureListener {
                Constants.showSnackBar(
                    view,
                    "فشل حذف التسليم",
                    Constants.redColor
                )
            }

    }


}
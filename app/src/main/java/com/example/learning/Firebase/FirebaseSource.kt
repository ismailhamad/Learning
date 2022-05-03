package com.example.learning.Firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.*

import com.example.learning.View.Student
import com.example.learning.View.Teacher
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlin.collections.ArrayList


class FirebaseSource(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
     lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var coursea: course? = null
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var CourseListMutableLiveData: MutableLiveData<List<course>>
    lateinit var favoriteListMutableLiveData: MutableLiveData<List<course>>
    lateinit var courseExploreListMutableLiveData: MutableLiveData<List<course>>
    lateinit var searchListMutableLiveData: MutableLiveData<List<course>>
    lateinit var CourseTeacherListMutableLiveData: MutableLiveData<List<course>>
    lateinit var show_StudentListMutableLiveData: MutableLiveData<List<course>>
    lateinit var MyCourseListMutableLiveData: MutableLiveData<List<myCourse>>
    lateinit var LectureListMutableLiveData: MutableLiveData<List<lecture>>
    lateinit var MessageListMutableLiveData: MutableLiveData<List<Chat>>
    lateinit var AssignmentListMutableLiveData: MutableLiveData<List<Assignment>>
    lateinit var usersListMutableLiveData: MutableLiveData<List<users>>
    lateinit var usersLectureListMutableLiveData: MutableLiveData<List<users>>
    lateinit var chatListMutableLiveData: MutableLiveData<List<Chat>>
    lateinit var chatListprivMutableLiveData: MutableLiveData<List<Chat>>
    lateinit var usersAddAssiListMutableLiveData: MutableLiveData<HashMap<String, Any?>>
    lateinit var course: course
    lateinit var countUserListprivMutableLiveData: MutableLiveData<Int>
    lateinit var countUserAddAssigmentListprivMutableLiveData: MutableLiveData<Int>
    var BuyONot: Boolean = false


    //Log in to the account
    fun Sign_in(view: View, Email: String, Password: String) {
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        analytics = Firebase.analytics
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseMessaging.getInstance().subscribeToTopic(auth.currentUser!!.uid)
                if (auth.currentUser!!.isEmailVerified) {
                    if (Email == "joehamad2060@gmail.com") {
                        val i = Intent(activity, Teacher::class.java)
                        activity.startActivity(i)
                        progressDialog.dismiss()
                        activity.finish()
                        analytics.setUserProperty("User_type","Teacher")
                    } else {
                        val i = Intent(activity, Student::class.java)
                        activity.startActivity(i)
                        progressDialog.dismiss()
                        activity.finish()
                        analytics.setUserProperty("User_type","Student")
                    }
                    analytics.logEvent("my_login") {
                        param("email_name", auth.currentUser!!.email.toString())
                    }

                } else {
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "يرجى التحقق من عنوان البريد الإلكتروني الخاص بك",
                        Constants.redColor
                    )
                }

            } else {
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "يرجى التحقق من اسم المستخدم أو كلمة السر الخاص بك",
                    Constants.redColor
                )
            }

        }


    }


    //Sign up for the account
    fun Sign_up(view: View, password: String, users: users) {
        auth = Firebase.auth
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        auth.createUserWithEmailAndPassword(users.email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    if (it.isSuccessful) {
                        createUser(
                            users(
                                auth.currentUser!!.uid,
                                users.name,
                                users.lastName,
                                users.email,
                                0
                            )
                        )
                        progressDialog.dismiss()
                        Constants.showSnackBar(
                            view,
                            "تم تسجيلك بنجاح. تفقد بريدك الالكتروني لتأكيد الحساب",
                            Constants.greenColor
                        )

                    } else {
                        progressDialog.dismiss()
                        Constants.showSnackBar(
                            view,
                            "فشل التحقق",
                            Constants.redColor
                        )

                    }
                }


                val user = auth.currentUser
            } else {
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تسجيل الحساب",
                    Constants.redColor
                )
            }
        }
    }


    //Create a user and put it in the firestore
    fun createUser(users: users) {
        db = Firebase.firestore
        auth = Firebase.auth
        db.collection("users").document(auth.currentUser!!.uid)
            .set(users).addOnSuccessListener {

            }

    }

    //addcousrs by lecture
    fun addCourse(view: View, course: course, imgeUri: Uri?) {

        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        analytics = Firebase.analytics
        imgeUri?.let {
            storageRef!!.child("image/" + "${course.id}").putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        course.image = uri.toString()
                        db.collection("courses").document(course.id.toString()).set(course)
                            .addOnSuccessListener {
                                Constants.showSnackBar(
                                    view,
                                    "تم اضافة الكورس",
                                    Constants.greenColor
                                )
                                analytics.logEvent("addCourse"){
                                    param("name_course", course.namecourse!!)
                                }

                            }
                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل اضافة الكورس",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }


    }


    //getAllCourse
    fun getCourse(): MutableLiveData<List<course>> {
        db = Firebase.firestore
        val Courselist = ArrayList<course>()
        CourseListMutableLiveData = MutableLiveData()
        db.collection("courses").orderBy("time",Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            for (document in value!!) {
                val course = document.toObject<course>()
                Courselist.add(course)
                CourseListMutableLiveData.postValue(Courselist)
            }


        }

        return CourseListMutableLiveData

    }

    //احضار دورات المعلم الخاصة به
    fun getTeacherCourse(uid: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val Courselist = ArrayList<course>()
        CourseTeacherListMutableLiveData = MutableLiveData()
        db.collection("courses").addSnapshotListener() { result, error ->
            for (courses in result!!) {
                val course = courses!!.toObject<course>()
                if (uid == course.idTeacher) {
                    course?.let { Courselist.add(it) }
                    CourseTeacherListMutableLiveData.postValue(Courselist)
                }

            }

        }


        return CourseTeacherListMutableLiveData

    }

    fun getStudentrCourse(uid: String, documentCourses: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val Courselist = ArrayList<course>()
        show_StudentListMutableLiveData = MutableLiveData()
        db.collection("courses").document(documentCourses).addSnapshotListener() { result, error ->

            val course = result!!.toObject<course>()
            if (uid == course?.idTeacher) {
                course.let { Courselist.add(it) }
                show_StudentListMutableLiveData.postValue(Courselist)
            }

        }




        return show_StudentListMutableLiveData

    }


    //addMyCourse by student
    fun addMyCourse(view: View, myCourse: course) {
        db = Firebase.firestore
        auth = Firebase.auth
        analytics = Firebase.analytics
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        var bool = false
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener { it ->
            val users = it.toObject<users>()
            if (users!!.numCourse!! < 5) {
                db.collection("myCourse").document(myCourse.id!!).set(myCourse)
                    .addOnSuccessListener {
                        Constants.showSnackBar(
                            view,
                            "تم اضافة الكورس",
                            Constants.greenColor
                        )
                        progressDialog.dismiss()
                        analytics.logEvent("addMyCourse"){
                            param("name_course", course.namecourse!!)
                        }
                    }

            } else {
                //Alert
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "لا يمكن إضافة أكثر من 5 كورسات",
                    Constants.redColor
                )
            }

        }


    }

    fun getMyCourse(): MutableLiveData<List<myCourse>> {
        db = Firebase.firestore
        val Courselist = ArrayList<myCourse>()
        MyCourseListMutableLiveData = MutableLiveData()
        auth = Firebase.auth
        db.collection("myCourse").addSnapshotListener { result, error ->
            for (document in result!!) {
                val course = document.toObject<myCourse>()
                val cousree = course.users
                if (cousree != null) {
                    for (users in cousree) {
                        if (auth.currentUser!!.uid == users.id) {
                            Courselist.add(course)
                            updatenumCourse(Courselist.count())
                            MyCourseListMutableLiveData.postValue(Courselist)
                        }
                    }
                }


            }
        }

        return MyCourseListMutableLiveData

    }


    // Atomically add a new region to the "users" array field.
    suspend fun updateUsers(view: View, idCourse: String, users: users) {
        val users = hashMapOf(
            "id" to users.id,
            "name" to users.name,
            "lastName" to users.lastName,
            "email" to users.email
        ) as Map<String, Any>

        val washingtonRef = db.collection("courses").document(idCourse)

// Atomically add a new region to the "users" array field.
        washingtonRef.update("users", FieldValue.arrayUnion(users)).addOnSuccessListener {
            db.collection("courses").document(idCourse).addSnapshotListener { value, error ->
                val course = value!!.toObject<course>()
                addMyCourse(view, course!!)
            }
        }


    }


    //Atomically add a new region to the "numCourse" array field.
    fun updatenumCourse(numberr: Int) {

        val washingtonRef = db.collection("users").document(auth.currentUser!!.uid)

// Atomically add a new region to the "users" array field.
        washingtonRef.update("numCourse", numberr).addOnSuccessListener {


        }
    }


    fun getLecture(document: String): MutableLiveData<List<lecture>> {
        db = Firebase.firestore
        LectureListMutableLiveData = MutableLiveData()
        db.collection("courses").document(document).collection("lecture")
            .orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { result, error ->
                val course11 = result!!.toObjects<lecture>()

                LectureListMutableLiveData.postValue(course11)
            }
        return LectureListMutableLiveData

    }

    fun getUser(): MutableLiveData<List<users>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val userslist = ArrayList<users>()
        usersListMutableLiveData = MutableLiveData()
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val users = it.toObject<users>()
            users?.let { it1 -> userslist.add(it1) }
            usersListMutableLiveData.postValue(userslist)
        }
        return usersListMutableLiveData
    }


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
        course = course()
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
                    analytics.logEvent("addLecture"){
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
                        analytics.logEvent("addLecture"){
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
                                    analytics.logEvent("addLecture"){
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
        course = course()
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
                    analytics.logEvent("seeLecture"){
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


    fun deleteCourse(view: View, document: String) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        db = Firebase.firestore
        db.collection("courses").document(document).get()
            .addOnSuccessListener {
                storageRef!!.child("image/${it.get("id")}").delete()
                db.collection("courses").document(document).delete()
                Constants.showSnackBar(
                    view,
                    "تم حذف الكورس",
                    Constants.greenColor
                )

            }

    }

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
                    analytics.logEvent("addAssignment"){
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

    //    fun sendMessageCourse(chat: Chat, documentMyCourses: String) {
//        db = Firebase.firestore
//        db.collection("myCourse/${documentMyCourses}/message").document(chat.id)
//            .set(chat.getMessageHashMap())
//    }
    fun sendMessageCourse(chat: Chat, documentMyCourses: String, imgeUri: Uri?) {
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        storge = Firebase.storage
        storageRef = storge!!.reference
        if (imgeUri != null){
            Log.e("aa","imgeUri Fi $imgeUri")
            storageRef!!.child("imageMessageCourse/" + "${chat.id}").putFile(imgeUri)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        chat.image = uri.toString()
                        db.collection("myCourse/${documentMyCourses}/message").document(chat.id)
                            .set(chat.getMessageHashMap())
                    }
                }.addOnFailureListener { exception ->
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }else{
            Log.e("aa","imgeUri els $imgeUri")
            progressDialog.dismiss()
            db.collection("myCourse/${documentMyCourses}/message").document(chat.id)
                .set(chat.getMessageHashMap())
        }



    }

    fun deleteMessageCourse(documentMyCourses: String, documentChat: String) {
        db = Firebase.firestore
        db.collection("myCourse/${documentMyCourses}/message").document(documentChat).delete()
    }

    fun getMessageCourse(documentMyCourses: String): MutableLiveData<List<Chat>> {
        db = Firebase.firestore
        val chatList: ArrayList<Chat> = arrayListOf()
        chatListMutableLiveData = MutableLiveData()
        db.collection("myCourse/${documentMyCourses}/message")
            .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                chatList.clear()
                for (document in value!!) {
                    val chat = document.toObject<Chat>()
                    chatList.add(chat)
                    chatListMutableLiveData.postValue(chatList)
                }
            }
        return chatListMutableLiveData
    }

    fun sendMessagePrivate(chat: Chat, documentUsers: String) {
        db = Firebase.firestore
        db.collection("users/${documentUsers}/message").document(chat.id)
            .set(chat.getMessageHashMap())
    }

    fun deleteMessagePrivate(documentUsers: String, documentChat: String) {
        db = Firebase.firestore
        db.collection("users/${documentUsers}/message").document(documentChat).delete()
    }

    fun getMessagePrivate(documentUsers: String): MutableLiveData<List<Chat>> {
        db = Firebase.firestore
        val chatList = ArrayList<Chat>()
        chatListprivMutableLiveData = MutableLiveData()
        db.collection("users/${documentUsers}/message").orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                chatList.clear()
                for (document in value!!) {
                    val chat = document.toObject<Chat>()
                    chatList.add(chat)
                    chatListprivMutableLiveData.postValue(chatList)
                }
            }
        return chatListprivMutableLiveData
    }

    fun deleteMyCourse(view: View, users: users, document: String) {
        db = Firebase.firestore
        val userss = hashMapOf(
            "id" to users.id,
            "name" to users.name,
            "lastName" to users.lastName,
            "email" to users.email
        ) as Map<String, Any>

        db.collection("myCourse").document(document)
            .update("users", FieldValue.arrayRemove(userss))
            .addOnSuccessListener {
                db.collection("courses").document(document)
                    .update("users", FieldValue.arrayRemove(userss))
                    .addOnSuccessListener {
                    }

                Constants.showSnackBar(
                    view,
                    "تم حذف الكورس",
                    Constants.greenColor
                )
            }.addOnFailureListener {
                Constants.showSnackBar(
                    view,
                    "فشل حذف الكورس",
                    Constants.redColor
                )
            }


    }

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
                .document(
                    it
                ).addSnapshotListener { value, error ->
                    val idusers = value?.get("id")
                    val file = value?.get("file")
                    val data = hashMapOf<String, Any?>(
                        "id" to idusers,
                        "file" to file
                    )
                    usersAddAssiListMutableLiveData?.postValue(data)
                }

        }

        return usersAddAssiListMutableLiveData

    }


    fun userAddAssignment(
        view: View,
        users: users,
        documentCourses: String,
        documentLecture: String,
        documentAssignment: String,
        file: Uri,
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
                    analytics.logEvent("userAddAssignment"){
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


    fun getUserShowLecture(
        documentCourses: String,
        documentLecture: String,
    ): MutableLiveData<List<users>> {
        db = Firebase.firestore
        usersLectureListMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users")
            .addSnapshotListener { result, error ->
                val user = result!!.toObjects<users>()

                usersLectureListMutableLiveData.postValue(user)
            }
        return usersLectureListMutableLiveData

    }


    fun showUserLecture(
        users: users,
        documentCourses: String,
        documentLecture: String,
    ) {
        db = Firebase.firestore
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users")
            .document(users.id!!)
            .set(users)
    }

   fun searchCourse(text: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        val Courselist = ArrayList<course>()
        searchListMutableLiveData = MutableLiveData()
        db.collection("courses").whereEqualTo("namecourse",text).addSnapshotListener { value, error ->
            for (item in value!!) {
                val course = item.toObject<course>()
                Courselist.add(course)
                searchListMutableLiveData.postValue(Courselist)
            }
            analytics.logEvent("searchCourse"){
                param("name_course", course.namecourse!!)
            }
        }

        return searchListMutableLiveData
    }

    fun addFavorite(view:View,course: course, documentUsers: String) {
        db = Firebase.firestore
        analytics = Firebase.analytics
        db.collection("users/${documentUsers}/Favorite").document(course.id!!)
            .set(course).addOnSuccessListener {
                Constants.showSnackBar(
                    view,
                    "تم اضافة الكورس الى المفضلة",
                    Constants.greenColor
                )
                analytics.logEvent("addFavorite"){
                    param("name_course", course.namecourse!!)
                }
            }.addOnFailureListener {
                Constants.showSnackBar(
                    view,
                    "فشل اضافة الكورس الى المفضلة",
                    Constants.redColor
                )
            }
    }

    fun getFavorite(documentUsers: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        val favoritelist = ArrayList<course>()
        favoriteListMutableLiveData = MutableLiveData()
        db.collection("users/${documentUsers}/Favorite").addSnapshotListener { value, error ->
            for (document in value!!) {
                val course = document.toObject<course>()
                favoritelist.add(course)
                favoriteListMutableLiveData.postValue(favoritelist)
            }


        }

        return favoriteListMutableLiveData

    }

    fun getCourseExplore(): MutableLiveData<List<course>> {
        db = Firebase.firestore
        val Courselist = ArrayList<course>()
        courseExploreListMutableLiveData = MutableLiveData()
        db.collection("courses").orderBy("time",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            for (document in value!!) {
                val course = document.toObject<course>()
                Courselist.add(course)
                courseExploreListMutableLiveData.postValue(Courselist)
            }


        }

        return courseExploreListMutableLiveData

    }

    fun deleteFavorite(view:View,documentUsers: String,documentCourses: String) {
        db = Firebase.firestore
        db.collection("users/${documentUsers}/Favorite").document(documentCourses).delete()
            .addOnSuccessListener {
                Constants.showSnackBar(
                    view,
                    "تم حذف الكورس من المفضلة",
                    Constants.greenColor
                )
            }.addOnFailureListener {
                Constants.showSnackBar(
                    view,
                    "فشل حذف الكورس",
                    Constants.redColor
                )
            }
    }

    fun getCountUserShowLecture(
        documentCourses: String,
        documentLecture: String,
    ): MutableLiveData<Int> {
        db = Firebase.firestore
        var count = 0
        countUserListprivMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users").get().addOnSuccessListener {
            for (i in it){
                countUserListprivMutableLiveData.postValue(count++)
            }
        }

        return countUserListprivMutableLiveData

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
            db.collection("courses/${documentCourses}/lecture/${documentLecture}/assignment/${documentAssignment}/userAssignment").get().addOnSuccessListener {
                for (i in it){
                    Log.e("aa","name ddd ${i.get("id")}")
                    countUserAddAssigmentListprivMutableLiveData.postValue(++count)
                }
                }

        return countUserAddAssigmentListprivMutableLiveData

    }



}








package com.example.learning.Firebase.Student

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseSourceCourseST(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var coursea: course? = null
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var CourseListMutableLiveData: MutableLiveData<List<course>>
    lateinit var show_StudentListMutableLiveData: MutableLiveData<List<course>>
    lateinit var MyCourseListMutableLiveData: MutableLiveData<List<myCourse>>
    lateinit var searchListMutableLiveData: MutableLiveData<List<course>>
    lateinit var favoriteListMutableLiveData: MutableLiveData<List<course>>
    lateinit var courseExploreListMutableLiveData: MutableLiveData<List<course>>


    fun getCourse(): MutableLiveData<List<course>> {
        db = Firebase.firestore
        val Courselist = ArrayList<course>()
        CourseListMutableLiveData = MutableLiveData()
        db.collection("courses").orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                Courselist.clear()
                for (document in value!!) {
                    val course = document.toObject<course>()
                    Courselist.add(course)
                    CourseListMutableLiveData.postValue(Courselist)
                }


            }

        return CourseListMutableLiveData

    }


    fun getStudentrCourse(uid: String, documentCourses: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val Courselist = ArrayList<course>()
        show_StudentListMutableLiveData = MutableLiveData()
        db.collection("courses").document(documentCourses).addSnapshotListener() { result, error ->
            Courselist.clear()
            val course = result!!.toObject<course>()
            if (uid == course?.idTeacher) {
                course.let { Courselist.add(it) }
                show_StudentListMutableLiveData.postValue(Courselist)
            }

        }




        return show_StudentListMutableLiveData

    }



     fun updateUsers(view: View, idCourse: String, users: users) {
        val users = hashMapOf(
            "id" to users.id,
            "name" to users.name,
            "lastName" to users.lastName,
            "email" to users.email
        ) as Map<String, Any>

        val washingtonRef = db.collection("courses").document(idCourse)

// Atomically add a new region to the "users" array field.
        washingtonRef.update("users", FieldValue.arrayUnion(users)).addOnSuccessListener {
            db.collection("courses").document(idCourse).get().addOnSuccessListener { value ->
                val course = value!!.toObject<course>()
                addMyCourse(view, course!!)
            }
        }


    }

    fun addMyCourse(view: View, myCourse: course) {
        db = Firebase.firestore
        auth = Firebase.auth
        analytics = Firebase.analytics
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener { it ->
            val users = it.toObject<users>()
            if (users!!.numCourse!! < 5) {
                db.collection("myCourse").document(myCourse.id!!).set(myCourse)
                    .addOnSuccessListener {
                        Snackbar.make(view, "تم اضافة الكورس", Snackbar.LENGTH_LONG).apply {
                            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                            setBackgroundTint(Color.parseColor(Constants.greenColor))
                            setTextColor(Color.parseColor("#FFFFFF"))
                            show()
                        }
                        analytics.logEvent("addMyCourse") {
                            param("name_course", myCourse!!.namecourse!!)
                        }
                    }

            } else {
                //Alert

                Snackbar.make(view, "لا يمكن اضافة اكثر من ٥ كورسات", Snackbar.LENGTH_LONG).apply {
                    animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                    setBackgroundTint(Color.parseColor(Constants.greenColor))
                    setTextColor(Color.parseColor("#FFFFFF"))
                    show()
                }
            }

        }


    }






    fun getMyCourse(): MutableLiveData<List<myCourse>> {
        db = Firebase.firestore
        val Courselist = ArrayList<myCourse>()
        MyCourseListMutableLiveData = MutableLiveData()
        auth = Firebase.auth
        db.collection("myCourse").addSnapshotListener { result, error ->
            Courselist.clear()
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
    //Atomically add a new region to the "numCourse" array field.
    fun updatenumCourse(numberr: Int) {
        val washingtonRef = db.collection("users").document(auth.currentUser!!.uid)
// Atomically add a new region to the "users" array field.
        washingtonRef.update("numCourse", numberr).addOnSuccessListener {
        }
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
                        Snackbar.make(view, "تم حذف الكورس", Snackbar.LENGTH_LONG).apply {
                            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                            setBackgroundTint(Color.parseColor(Constants.greenColor))
                            setTextColor(Color.parseColor("#FFFFFF"))
                            show()
                        }
                    }
            }.addOnFailureListener {
                Snackbar.make(view, "فشل حذف الكورس", Snackbar.LENGTH_LONG).apply {
                    animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                    setBackgroundTint(Color.parseColor(Constants.redColor))
                    setTextColor(Color.parseColor("#FFFFFF"))
                    show()
                }
            }


    }

    fun searchCourse(text: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        analytics = Firebase.analytics
        val Courselist = ArrayList<course>()
        searchListMutableLiveData = MutableLiveData()
        db.collection("courses").whereEqualTo("namecourse",text)
            .addSnapshotListener { value, error ->
                Courselist.clear()
                if (value!!.isEmpty) {
                    searchListMutableLiveData.postValue(null)
                } else {
                    for (item in value!!) {
                        val course = item.toObject<course>()
                        Courselist.add(course)
                        searchListMutableLiveData.postValue(Courselist)
                    }
                    analytics.logEvent("searchCourse") {
                        param("search_name", text)
                    }
                }
            }
        return searchListMutableLiveData
    }

    fun addFavorite(view: View, course: course, documentUsers: String) {
        db = Firebase.firestore
        analytics = Firebase.analytics
        db.collection("users/${documentUsers}/Favorite").document(course.id!!)
            .set(course).addOnSuccessListener {
                Constants.showSnackBar(
                    view,
                    "تم اضافة الكورس الى المفضلة",
                    Constants.greenColor
                )
                analytics.logEvent("addFavorite") {
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
            favoritelist.clear()
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
        db.collection("courses").orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                for (document in value!!) {
                    val course = document.toObject<course>()
                    Courselist.add(course)
                    courseExploreListMutableLiveData.postValue(Courselist)
                }


            }

        return courseExploreListMutableLiveData

    }

    fun deleteFavorite(view: View, documentUsers: String, documentCourses: String) {
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
}
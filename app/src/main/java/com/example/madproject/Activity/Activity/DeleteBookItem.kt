package com.example.madproject.Activity.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DeleteBookItem : AppCompatActivity() {


    val db = Firebase.firestore
    private lateinit var userid : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

//    val deletemaster = findViewById<ImageButton>(R.id.deletebookE)
//    val temmptitle = findViewById<TextView>(R.id.book_title)

    var storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        println(temmptitle.text)

//        //get title when button clicked
//        deletemaster.setOnClickListener {
//            val title = intent.getStringExtra("title")
//            deleteBook(title)
//        }

    }

    //delete book from the database
    fun deleteBook(title: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val collectionRef = firebaseFirestore.collection("Books").whereEqualTo("userId", userid).whereEqualTo("title", title)

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val id = document.id
                val docRef = firebaseFirestore.collection("Books").document(id)
                docRef.delete()
            }
        }
    }
}
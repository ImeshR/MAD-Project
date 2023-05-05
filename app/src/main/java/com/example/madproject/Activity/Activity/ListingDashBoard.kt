package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class ListingDashBoard : AppCompatActivity() {

    private lateinit var  navbtnUpload: ImageView
    private lateinit var navbtnDelete: ImageView
    private lateinit var recycler_view : RecyclerView

    val db = Firebase.firestore
    private lateinit var userid : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.madproject.R.layout.activity_listing_dash_board)
        displayBookDetails()
        getCount()


        navbtnUpload  = findViewById(com.example.madproject.R.id.addbook)
        navbtnDelete = findViewById(com.example.madproject.R.id.deletebook)

        navbtnUpload.setOnClickListener {
            val intent = Intent(this, Insert_Book::class.java)
            startActivity(intent)
        }
        navbtnDelete.setOnClickListener {
            val intent = Intent(this, DeleteBookList::class.java)
            startActivity(intent)
        }

//        recycler_view = findViewById(R.id.recycler_view)
//
//        recycler_view.layoutManager = LinearLayoutManager(this)
//
//        val books = listOf(
//            Book("Book 1",  "https://firebasestorage.googleapis.com/v0/b/madproject-7a05c.appspot.com/o/images%2F417a1c96-b20d-4279-84d1-74993d9ba984?alt=media&token=bb382dd9-ed6d-407e-8534-c0a2ac033852"),
//            Book("Book 2",  "https://firebasestorage.googleapis.com/v0/b/madproject-7a05c.appspot.com/o/images%2F9fcda4c6-5de1-427f-8536-f30dfe22fb1c?alt=media&token=ee6620b5-3073-4cff-8076-dfe926010432")
//        )
//
//        val adapter = BookAdapter(books)
//        recycler_view .adapter = adapter

    }

    //get count
    fun getCount(){
        // specify the collection name and user id
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""



        println("User ID: $userid")

        val collectionRef = db.collection("Books").whereEqualTo("userId", userid)

        // get the count of documents in the collection
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            val count = querySnapshot.size()
            println("Total Books for userId $userid:$count")


            // update the TextView with the count value
            val countlis = findViewById<TextView>(com.example.madproject.R.id.countlist)
            countlis.text = count.toString()

        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error getting documents: ", e)
        }

    }


    fun displayBookDetails(){
        // specify the collection name and user id
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        println("User second ID: $userid")


        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val collectionRef = firebaseFirestore.collection("Books").whereEqualTo("userId", userid)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val books = mutableListOf<Book>()

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val title = document.get("title") as String?
                val imager = document.get("imageUrl") as String?


                val book = Book("$title", "$imager")
                books.add(book)


            }
            val adapter = BookAdapter(books)
            recyclerView.adapter = adapter
        }

    }




}

package com.example.madproject.Activity.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R.id
import com.example.madproject.R.layout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class ListingDashBoard : AppCompatActivity() {

    private lateinit var  navbtnUpload: ImageView
    private lateinit var navbtnDelete: ImageView
    private  lateinit var navbtnup : ImageView

    val db = Firebase.firestore
    private lateinit var userid : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_listing_dash_board)
        displayBookDetails()
        getCount()


        navbtnUpload  = findViewById(id.addbook)
        navbtnDelete = findViewById(id.deletebook)
        navbtnup = findViewById(id.updatenav)

        navbtnUpload.setOnClickListener {
            val intent1 = Intent(this, Insert_Book::class.java)
            startActivity(intent1)
        }

        navbtnDelete.setOnClickListener {
            val intent2 = Intent(this, DeleteBookList::class.java)
            startActivity(intent2)
        }

        navbtnup.setOnClickListener {
            val intent3 = Intent(this, UpdateBook::class.java)
            startActivity(intent3)
        }
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
            val countlis = findViewById<TextView>(id.countlist)
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


        val recyclerView = findViewById<RecyclerView>(id.recycler_view3)
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

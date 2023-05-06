package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UpdateBook : AppCompatActivity() {

    private lateinit var recycler_view : RecyclerView

    val db = Firebase.firestore
    private lateinit var userid : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_book)
        displayBookDetails()
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


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val books = mutableListOf<Book>()
        val adapter = BookUpdateAdapter(books)

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val title = document.get("title") as String?
                val imager = document.get("imageUrl") as String?
                val bookId = document.id

                val book = Book("$title", "$imager")
                books.add(book)

                //set up update button for each page
                adapter.UpdateButtonClickListener = {

//                    println(title)

                    var Intent = Intent(this, Update_each_book::class.java)
                    Intent.putExtra("temptitle", title)
                    startActivity(Intent)
                }

            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }

    }

}
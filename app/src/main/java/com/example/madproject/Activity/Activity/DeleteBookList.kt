package com.example.madproject.Activity.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DeleteBookList : AppCompatActivity() {


    val db = Firebase.firestore
    private lateinit var userid: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_book_list)

        displayBookDetails()

    }


    fun displayBookDetails() {
        // specify the collection name and user id
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val collectionRef = firebaseFirestore.collection("Books").whereEqualTo("userId", userid)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val books = arrayListOf<BookD>()
        val adapter = BookDeleteAdapter(books)

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val title = document.get("title") as String?
                val imager = document.get("imageUrl") as String?
                val bookId = document.id

                val book = BookD("$title", "$imager")
                books.add(book)


                // Set up delete button for each book
                adapter.deleteButtonClickListener = {
                    deleteBook(bookId, imager)
                }

            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }


    }

    private fun deleteBook(bookId: String, ImageUrl: String?) {


        val imageUrl = ImageUrl// Replace with the actual image URL
        val uri = Uri.parse(imageUrl)
        val imageName = uri.lastPathSegment

        println("Check Image name is $imageName")


        // Delete book document from Firestore
        val bookRef = firebaseFirestore.collection("Books").document(bookId)
        bookRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "Book document $bookId deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting book document $bookId", e)
            }



        // Delete book image from Firebase Storage
        val imageRef = storage.reference.child("images/$imageName")
        imageRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "Book image $bookId deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting book image $bookId", e)
            }

        val intent = Intent(this,  DeleteBookList::class.java)
        startActivity(intent)
    }


}



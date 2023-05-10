package com.example.madproject.Activity.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


val db = Firebase.firestore
private lateinit var userid: String
private lateinit var firebaseAuth: FirebaseAuth
private lateinit var firebaseFirestore: FirebaseFirestore

var storage = FirebaseStorage.getInstance()

// Create a list of books
private val books = mutableListOf<Book>()

/**
 * A simple [Fragment] subclass.
 * Use the [Uni_book_category.newInstance] factory method to
 * create an instance of this fragment.
 */
class Uni_book_category : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        displaybook()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_uni_book_category, container, false)

        // call recyclerView in fragment
        val recyclerView = view?.findViewById<RecyclerView>(R.id.UniView)
        if (recyclerView != null) {
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
        }

        // Create an instance of the adapter and set it to the recyclerView
        val adapter = BookHomeAdapter(books)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }

        // call the displaybook() method to fetch books from Firestore and update the adapter


        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Uni_book_category.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Uni_book_category().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun displaybook() {

        val collectionRef = db.collection("Books").whereEqualTo("category", "University")

        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // access the fields of each document
                    val title = document.getString("title")
                    val imager = document.getString("imageUrl")
                    val documentid = document.id

                    println("title: $title")
                    println("imager: $imager")

                    val book = Book("$title", "$imager")
                    books.add(book)
                }
            }
        // update the adapter
//        adapter.notifyDataSetChanged()
    }
}
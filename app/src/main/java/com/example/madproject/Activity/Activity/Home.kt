package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val db = Firebase.firestore
    private lateinit var userid: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()

    // Create a list of books
    private val books = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // call recyclerView in fragment
        val recyclerView = view.findViewById<RecyclerView>(R.id.homerecycleview)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // Create an instance of the adapter and set it to the recyclerView
        val adapter = BookHomeAdapter(books)
        recyclerView.adapter = adapter

        // call the displaybook() method to fetch books from Firestore and update the adapter
        displaybook(adapter)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    // fetch books from Firestore and update the adapter
    private fun displaybook(adapter: BookHomeAdapter) {

        val collectionRef = db.collection("Books")

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


                    adapter.DataButtonClickListener ={
                        if (title != null) {
                            replaceFragment(title)
                        }
                    }


                }
                // update the adapter
                adapter.notifyDataSetChanged()
            }
    }
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = activity?.supportFragmentManager
//        val fragmentTransaction = fragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(R.id.homefragment, fragment)
//        fragmentTransaction?.commit()
//    }

    private fun replaceFragment(bookTitle: String) {
        val newFragment = newonedataview()
        val args = Bundle()
        args.putString("book_title", bookTitle)
        newFragment.arguments = args
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.homefragment, newFragment)
        transaction?.commit()
    }


}

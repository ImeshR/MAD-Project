package com.example.madproject.Activity.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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
 * Use the [newonedataview.newInstance] factory method to
 * create an instance of this fragment.
 */
class newonedataview : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var specialprice : Double = 0.0

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
        val view = inflater.inflate(R.layout.fragment_newonedataview, container, false)

        var backbtn = view?.findViewById<ImageView>(R.id.imageButton)
        var cartnav = view?.findViewById<Button>(R.id.viewbuybtn2)

        if (backbtn != null) {
            backbtn.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        val args = arguments
        val bookTitle = args?.getString("book_title")

        if (bookTitle != null) {
            displaydata(bookTitle)
        }

        cartnav?.setOnClickListener {
            replaceFragment(specialprice)
        }

        return view
    }
    fun onBackPressed(view: View) {
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment newonedataview.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            newonedataview().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun displaydata(tempIDE: String) {


        println("tempid mean eka thama ameka : $tempIDE")

        val db = Firebase.firestore
        lateinit var userid: String
        lateinit var firebaseAuth: FirebaseAuth
        lateinit var firebaseFirestore: FirebaseFirestore

        var title : String



        var storage = FirebaseStorage.getInstance()

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val collectionRef = firebaseFirestore.collection("Books").whereEqualTo("title", tempIDE)

        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var title = document.getString("title")
                    val description = document.getString("description")
                    val image = document.getString("imageUrl")
                    val category = document.getString("category")
                    val srteam = document.getString("stream")
                    val price = document.getDouble("price")

                    price?.let { specialprice = it }

                    println("description : $description")
                    println("title : $title")
                    println("image : $image")
                    println("category : $category")
                    println("srteam : $srteam")
                    println("price : $price")



                    var titleview = view?.findViewById<TextView>(R.id.viewtitle)
                    var descriptionview = view?.findViewById<TextView>(R.id.viewdescription)
                    var imageview = view?.findViewById<ImageView>(R.id.viewdatapic)
                    var categoryview = view?.findViewById<TextView>(R.id.viewcat)
                    var streamview = view?.findViewById<TextView>(R.id.viewstream)
                    var priceview = view?.findViewById<TextView>(R.id.viewprice)

                    if (titleview != null) {
                        titleview.text = title
                    }
                    if (descriptionview != null) {
                        descriptionview.text = description
                    }
                    if (categoryview != null) {
                        categoryview.text = category
                    }
                    if (streamview != null) {
                        streamview.text = srteam
                    }
                    if (priceview != null) {
                        priceview.text = price.toString()
                    }


                    // Load image using Glide library
                    if (imageview != null) {
                        Glide.with(this)
                            .load(image)
                            .into(imageview)
                    }

                }
            }


    }

    private fun replaceFragment(bookprice: Double) {

        val newFragment = Cart()
        val args = Bundle()
        args.putDouble("book_price", bookprice)

        newFragment.arguments = args
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.homefragment, newFragment)
        transaction?.commit()
    }

}
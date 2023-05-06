package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class BookViewData : AppCompatActivity() {

    lateinit var tempid: String
    var navcartbtn : Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_view_data)

        tempid = intent.getStringExtra("temptitle").toString()

        displaydata(tempid)

        var backbtn = findViewById<ImageView>(R.id.viewbackbtn)
        //back to dashboard
        backbtn.setOnClickListener {
            finish()
//            var intent = Intent(this, Home::class.java)
//            startActivity(intent)
        }


        navcartbtn = findViewById(R.id.viewbuybtn)

        navcartbtn!!.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val fragment = Cart()
            fragmentTransaction.add(R.id.cartshehan, fragment)
            fragmentTransaction.commit()
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

                    println("description : $description")
                    println("title : $title")
                    println("image : $image")
                    println("category : $category")
                    println("srteam : $srteam")
                    println("price : $price")


                    var titleview = findViewById<TextView>(R.id.viewtitle)
                    var descriptionview = findViewById<TextView>(R.id.viewdescription)
                    var imageview = findViewById<ImageView>(R.id.viewdatapic)
                    var categoryview = findViewById<TextView>(R.id.viewcat)
                    var streamview = findViewById<TextView>(R.id.viewstream)
                    var priceview = findViewById<TextView>(R.id.viewprice)

                    titleview.text = title
                    descriptionview.text = description
                    categoryview.text = category
                    streamview.text = srteam
                    priceview.text = price.toString()


                    // Load image using Glide library
                    Glide.with(this)
                        .load(image)
                        .into(imageview)


                }
            }


    }
}
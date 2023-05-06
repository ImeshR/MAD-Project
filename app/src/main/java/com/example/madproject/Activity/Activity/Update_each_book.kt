package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class Update_each_book : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var userid: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    var storage = FirebaseStorage.getInstance()


    private lateinit var imageView: ImageView
    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addressEditText: EditText
    private lateinit var uploadButton: Button
    private lateinit var spinner2_al: Spinner
    private lateinit var spinner2_university: Spinner

    lateinit var streamname: String
    var tempTitle: String? = null
    var tempdocid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_each_book)

        tempTitle = intent.getStringExtra("temptitle")


        // Initialize views
        imageView = findViewById(R.id.imageView)
        titleEditText = findViewById(R.id.titleEditText)
        priceEditText = findViewById(R.id.priceEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        categorySpinner = findViewById(R.id.categorySpinner)
        addressEditText = findViewById(R.id.addressEditText)
        uploadButton = findViewById(R.id.uploadButton)
        spinner2_al = findViewById(R.id.spinner2_al)
        spinner2_university = findViewById(R.id.spinner2_university)

        //for spinner change only AL and University


        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        val spinner2_al = findViewById<Spinner>(R.id.spinner2_al)
        val adapter2_al = ArrayAdapter.createFromResource(
            this,
            R.array.spinner2_options_al,
            android.R.layout.simple_spinner_item
        )
        adapter2_al.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2_al.adapter = adapter2_al

        val spinner2_university = findViewById<Spinner>(R.id.spinner2_university)
        val adapter2_university = ArrayAdapter.createFromResource(
            this,
            R.array.spinner2_options_university,
            android.R.layout.simple_spinner_item
        )
        adapter2_university.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2_university.adapter = adapter2_university

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position) as String
                if (selectedItem == "AL") {
                    spinner2_al.visibility = View.VISIBLE
                    spinner2_university.visibility = View.GONE

                    streamname = spinner2_al.selectedItem.toString()

                } else if (selectedItem == "University") {
                    spinner2_al.visibility = View.GONE
                    spinner2_university.visibility = View.VISIBLE

                    streamname = spinner2_university.selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinner2_al.visibility = View.GONE
                spinner2_university.visibility = View.GONE
            }
        }


        var title: String? = null
        var price: Double? = null
        var description: String? = null
        var address: String? = null
        var imgurl: String? = null


        // specify the collection name and user id
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val collectionRef = firebaseFirestore.collection("Books").whereEqualTo("title", tempTitle)


        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                title = document.getString("title")
                price = document.getDouble("price")
                description = document.getString("description")
                address = document.getString("address")
                tempdocid = document.id
                imgurl = document.getString("imgurl")



                println("title: $title")
                println("price: $price")
                println("description: $description")
                println("address: $address")

                titleEditText.setText(title)
                priceEditText.setText(price.toString())
                descriptionEditText.setText(description)
                addressEditText.setText(address)

            }

        }

        uploadButton.setOnClickListener {
            updateBook(tempdocid.toString(), imgurl.toString(), userid)

            val intent3 = Intent(this, ListingDashBoard::class.java)
            startActivity(intent3)

        }


    }


    fun updateBook(docid: String, imgurl: String, userid: String) {
        val title = titleEditText.text.toString()
        val price = priceEditText.text.toString().toDouble()
        val description = descriptionEditText.text.toString()
        val address = addressEditText.text.toString()
        val category = categorySpinner.selectedItem.toString()
        val stream = streamname

        val book = BookU(
            "$title",
            "$imgurl",
            "$description",
            "$imgurl",
            "$price",
            "$stream",
            "$title",
            "$userid"
        )
        val bookRef = db.collection("Books").document(docid)
        bookRef.set(book)
            .addOnSuccessListener {
                println("Book updated")
                Toast.makeText(this, "Book Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                println("Error updating book")
            }

//    private fun updateBook(bookId: String) {
//        val intent = intent
//        val title = intent.getStringExtra("title")
//        val imager = intent.getStringExtra("imageUrl")
//        val book = Book("$title", "$imager")
//        val bookRef = db.collection("Books").document(bookId)
//        bookRef.set(book)
//            .addOnSuccessListener {
//                println("Book updated")
//            }
//            .addOnFailureListener {
//                println("Error updating book")
//            }
//
//
//        //update image
//        val storageRef = storage.reference
//        val imageRef = storageRef.child("images/$bookId")
//        val uploadTask = imageRef.putFile(Uri.parse(imager))
//        uploadTask.addOnFailureListener {
//            println("Error uploading image")
//        }.addOnSuccessListener {
//            println("Image uploaded")
//        }
//
//    }


    }
}
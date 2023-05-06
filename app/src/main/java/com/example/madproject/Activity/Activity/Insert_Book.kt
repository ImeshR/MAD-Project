package com.example.madproject.Activity.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*


class Insert_Book : AppCompatActivity() {
    // Views
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

    private lateinit var subCategorySpinner: AutoCompleteTextView
    private var categoryAdapter: ArrayAdapter<String>? = null
    private var subcategoryAdapter: ArrayAdapter<String>? = null


    // Image URI
    private var imageUri: Uri? = null

    // Firebase references
    private lateinit var storageRef: StorageReference
    private lateinit var firestoreDb: FirebaseFirestore


    private lateinit var userid: String
    private lateinit var firebaseAuth: FirebaseAuth

    // Upload progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_book)

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


        // Initialize Firebase references
        storageRef = FirebaseStorage.getInstance().reference
        firestoreDb = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        userid = firebaseAuth.currentUser?.uid ?: ""

        // Set up image selection
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Set up upload button
        uploadButton.setOnClickListener {
            uploadData()

            if (imageUri != null) {
                val intent = Intent(this, ListingDashBoard::class.java)
                startActivity(intent)
            }

        }


        //print user id on log cat
        println("User ID: $userid")

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

    }

    // Constants
    companion object {
        private const val REQUEST_IMAGE_PICK = 0
    }

    // Handle image selection result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }

    // Upload data to Firebase Storage and Firestore
    private fun uploadData() {

        //unit testing
        testinputs()

        // Show progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        // Set up image upload task
        val imageFilename = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$imageFilename")
        val uploadTask = imageRef.putFile(imageUri!!)

        // Continue with Firestore upload task after image upload completes
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            return@Continuation imageRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Get image download URL
                val downloadUri = task.result

                // Set up data for Firestore upload
                val data = hashMapOf(
                    "title" to titleEditText.text.toString(),
                    "price" to priceEditText.text.toString().toDouble(),
                    "description" to descriptionEditText.text.toString(),
                    "category" to categorySpinner.selectedItem.toString(),
                    "address" to addressEditText.text.toString(),
                    "imageUrl" to downloadUri.toString(),
                    "userId" to userid,
                    "stream" to streamname
                )

                // Upload data to Firestore
                firestoreDb.collection("Books").add(data).addOnSuccessListener {
                    // Dismiss progress dialog
                    progressDialog.dismiss()

                    // Show success message
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()

                    // Finish activity
                    finish()
                }.addOnFailureListener { e ->
                    // Dismiss progress dialog
                    progressDialog.dismiss()

                    // Show error message
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Dismiss progress dialog
                progressDialog.dismiss()

                // Show error message
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun testinputs() {
        // Validate image
        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }
    }
}


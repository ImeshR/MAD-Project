package com.example.madproject.Activity.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
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

    // Image URI
    private var imageUri: Uri? = null

    // Firebase references
    private lateinit var storageRef: StorageReference
    private lateinit var firestoreDb: FirebaseFirestore

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

        // Initialize Firebase references
        storageRef = FirebaseStorage.getInstance().reference
        firestoreDb = FirebaseFirestore.getInstance()

        // Set up image selection
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Set up upload button
        uploadButton.setOnClickListener {
            uploadData()
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
        // Validate image
        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }

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
                    "imageUrl" to downloadUri.toString()
                )

                // Upload data to Firestore
                firestoreDb.collection("items").add(data).addOnSuccessListener {
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
}

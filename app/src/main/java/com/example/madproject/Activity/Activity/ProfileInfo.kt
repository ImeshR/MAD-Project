package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.madproject.databinding.ActivityProfileInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileInfo : AppCompatActivity() {

    private lateinit var binding: ActivityProfileInfoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        // Fetch user data from Firestore
        val user = firebaseAuth.currentUser
        if (user != null) {
            val docRef = firebaseFirestore.collection("users").document(user.uid)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val firstName = documentSnapshot.getString("firstName")
                    val lastName = documentSnapshot.getString("lastName")
                    val email = documentSnapshot.getString("email")
                    val mobileNumber = documentSnapshot.getString("mobileNumber")

                    // Update UI with user data
                    binding.FNameInfo.text = firstName
                    binding.LNameInfo.text = lastName
                    binding.EmailProfileInfo.text = email
                    binding.ContactProfileInfo.text = mobileNumber
                } else {
                    Log.d(TAG, "User data not found")
                }
            }.addOnFailureListener { e ->
                Log.e(TAG, "Error fetching user data", e)
            }
        } else {
            Log.d(TAG, "No logged in user found")
        }
    }

    companion object {
        private const val TAG = "ProfileInfo"
    }
}
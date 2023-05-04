package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DeleteUserAccount : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        userId = firebaseAuth.currentUser?.uid ?: ""

        // Delete the user's document from Firestore
        firestore.collection("users").document(userId)
            .delete()
            .addOnSuccessListener {
                // Delete the user's authentication account
                firebaseAuth.currentUser?.delete()
                    ?.addOnSuccessListener {
                        Toast.makeText(this, "Account deleted successfully!", Toast.LENGTH_SHORT).show()
                        // Redirect to the Register page
                        startActivity(
                            android.content.Intent(this, MainNavigator::class.java)
                        )
//                        finish()
                    }
                    ?.addOnFailureListener { exception ->
                        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}
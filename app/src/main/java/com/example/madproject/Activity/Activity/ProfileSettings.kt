package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSettings : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val firstName: EditText = findViewById(R.id.editFirstName)
        val lastName: EditText = findViewById(R.id.editLastName)
        val userEmail: EditText = findViewById(R.id.editEmail)
        val mobileNumber: EditText = findViewById(R.id.editMobileNumber)
        val updateBtn: Button = findViewById(R.id.updateProfile)

        // fetch user data from Firestore
        db.collection("users")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val fName = document.getString("firstName")
                    val lName = document.getString("lastName")
                    val email = document.getString("email")
                    val number = document.getString("mobileNumber")

                    firstName.setText(fName)
                    lastName.setText(lName)
                    userEmail.setText(email)
                    mobileNumber.setText(number)
                } else {
                    Log.d("EditProfile", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("EditProfile", "get failed with ", exception)
                // handle exception here
            }

        updateBtn.setOnClickListener {
            val newFirstName = firstName.text.toString().trim()
            val newLastName = lastName.text.toString().trim()
            val newEmail = userEmail.text.toString().trim()
            val newNumber = mobileNumber.text.toString().trim()

            if (newFirstName.isEmpty() || newLastName.isEmpty() || newEmail.isEmpty() || newNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(newEmail)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPhoneNumber(newNumber)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a HashMap to hold the new user data
            val userUpdates = hashMapOf<String, Any>(
                "firstName" to newFirstName,
                "lastName" to newLastName,
                "email" to newEmail,
                "mobileNumber" to newNumber
            )

            // Update the user data in Firestore
            db.collection("users")
                .document(auth.currentUser!!.uid)
                .update(userUpdates)
                .addOnSuccessListener {
                    // Update the user email in Firebase Authentication
                    auth.currentUser!!.updateEmail(newEmail)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(this, ProfileInfo::class.java)
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.d("EditProfile", "update failed with ", exception)
                            // handle exception here
                        }
                }
                .addOnFailureListener { exception ->
                    Log.d("EditProfile", "update failed with ", exception)
                    // handle exception here
                }
        }

    }
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(number: String): Boolean {
        return number.length == 10 && TextUtils.isDigitsOnly(number)
    }
}
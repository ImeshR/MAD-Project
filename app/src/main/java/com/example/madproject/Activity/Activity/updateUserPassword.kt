package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.example.madproject.databinding.ActivityPrivacyBinding
import com.example.madproject.databinding.ActivityUpdateUserPasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class updateUserPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var binding: ActivityUpdateUserPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_password)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        currentUser = auth.currentUser!!

        binding = ActivityUpdateUserPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dprePass: EditText = findViewById(R.id.dprePass)
        val dNewpass: EditText = findViewById(R.id.dNewpass)
        val dConPass: EditText = findViewById(R.id.dConPass)
        val updateProfileNow: Button = findViewById(R.id.updateProfileNow)

        updateProfileNow.setOnClickListener {
            val prevPass = dprePass.text.toString().trim()
            val newPass = dNewpass.text.toString().trim()
            val conPass = dConPass.text.toString().trim()

            if (prevPass.isEmpty() || newPass.isEmpty() || conPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (newPass.length < 8) {
                Toast.makeText(this, "New password should be at least 8 characters long", Toast.LENGTH_SHORT).show()
            } else if (newPass != conPass) {
                Toast.makeText(this, "New password and Confirm password fields do not match", Toast.LENGTH_SHORT).show()
            } else {
                val credential = EmailAuthProvider.getCredential(currentUser.email!!, prevPass)

                currentUser.reauthenticate(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            currentUser.updatePassword(newPass)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        // Update password in Firestore
                                        db.collection("users").document(currentUser.uid)
                                            .update("password", newPass)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                                                binding.updateProfileNow.setOnClickListener {
                                                    startActivity(
                                                        Intent(this, Privacy::class.java)
                                                    )
                                                }

                                                finish()
                                            }
                                            .addOnFailureListener { exception ->
                                                Toast.makeText(this, "Failed to update password in Firestore", Toast.LENGTH_SHORT).show()
                                            }
                                    } else {
                                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            if (authTask.exception is FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(this, "Invalid previous password", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Failed to authenticate user", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
    }
}

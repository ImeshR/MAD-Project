package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCardAddFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class cardAdd_Form : AppCompatActivity() {

    private lateinit var binding: ActivityCardAddFormBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        super.onCreate(savedInstanceState)
        binding = ActivityCardAddFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitcardBtn.setOnClickListener{
            val s_name = binding.holderName.text.toString()
            val s_cardNum = binding.cardNum.text.toString()
            val s_month = binding.month.text.toString()
            val s_year = binding.year.text.toString()
            val s_cvv = binding.cvv.text.toString()

            // Add code to save card details to the Firebase database

            database = FirebaseDatabase.getInstance().getReference("Cards")
            val Card = Card(s_name, s_cardNum, s_month, s_year, s_cvv )
            database.child(s_name).setValue(Card).addOnSuccessListener {
                binding.holderName.text.clear()
                binding.cardNum.text.clear()
                binding.month.text.clear()
                binding.year.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
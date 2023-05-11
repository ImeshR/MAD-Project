package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.madproject.R
import com.example.madproject.databinding.ActivityUpdateCardBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCard : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCardBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToCardHandle.setOnClickListener {
            startActivity(
                Intent(this, CardHandle::class.java)
            )
        }

        val s_cardId = intent.getStringExtra("cardId")
        val s_cardNum = intent.getStringExtra("cardNumber")
        val s_exmonth = intent.getStringExtra("expMonth")
        val s_exyear = intent.getStringExtra("expYear")
        val s_cvv = intent.getStringExtra("cvv")

        binding.updateCardNumber.setText(s_cardNum)
        binding.updateExpMonth.setText(s_exmonth)
        binding.updateExpYear.setText(s_exyear)
        binding.updateCvv.setText(s_cvv)

        binding.updateCardBtn.setOnClickListener {
            // Perform payment processing and data insertion here
            updateCardData(s_cardId, s_cardNum, s_exmonth, s_exyear, s_cvv)
        }
    }

    private fun updateCardData(
        s_name: String?,
        s_cardNum: String?,
        s_exmonth: String?,
        s_exyear: String?,
        s_cvv: String?
    ) {
        val cardQuery = firestore.collection("cards")
            .whereEqualTo("CardNumber", s_cardNum)

        cardQuery.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    // If the payment exists, update its data
                    val paymentDocument = querySnapshot.documents[0]
                    val paymentId = paymentDocument.id
                    paymentDocument.reference.update(mapOf(
                        "cardHolderName" to s_name,
                        "expMonth" to s_exmonth,
                        "expYear" to s_exyear,
                        "cvv" to s_cvv
                    ))
                        .addOnSuccessListener {
                            Log.d("UpdateCard", "Card data updated successfully")
                            // Handle successful update, e.g., display a success message
                        }
                        .addOnFailureListener { e ->
                            Log.e("UpdateCard", "Error updating Card data", e)
                            // Handle update failure, e.g., display an error message
                        }
                } else {
                    // If the payment doesn't exist, display an error message or take appropriate action
                    Log.e("UpdateCard", "Card data not found for update: $s_cardNum")
                }
            }
            .addOnFailureListener { e ->
                Log.e("UpdateCard", "Error checking Card existence", e)
                // Handle the error
            }
    }
}

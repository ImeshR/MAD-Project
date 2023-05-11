package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCheckoutBinding
import com.google.firebase.firestore.FirebaseFirestore

class updatedPayment : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val address = intent.getStringExtra("address")
        val zip = intent.getStringExtra("zip")
        val city = intent.getStringExtra("city")
        val tp = intent.getStringExtra("tp")
        val price = intent.getStringExtra("checkoutprice")

        binding.checkoutaddress.text = address
        binding.checoutzip.text = zip
        binding.checkoutcity.text = city
        binding.checkouttp.text = tp
        binding.checkoutprice.text = price

        binding.checkoutpaymentbtn.setOnClickListener {
            // Perform payment processing and data insertion here
            updatePaymentData(address, zip, city, tp, price)
        }


    }

    private fun updatePaymentData(
        address: String?,
        zip: String?,
        city: String?,
        tp: String?,
        price: String?
    ) {
        val paymentQuery = firestore.collection("payments")
            .whereEqualTo("address", address)
            .whereEqualTo("zip", zip)
            .whereEqualTo("city", city)
            .whereEqualTo("tp", tp)

        paymentQuery.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    // If the payment exists, update its data
                    val paymentDocument = querySnapshot.documents[0]
                    val paymentId = paymentDocument.id
//                    val updatedPayment = hashMapOf(
//                        "address" to address,
//                        "zip" to zip,
//                        "city" to city,
//                        "tp" to tp,
//                        "price" to price
//                    )
                    paymentDocument.reference.update( mapOf(
                        "address" to address,
                        "zip" to zip,
                        "city" to city,
                        "tp" to tp,
                        "price" to price
                    ))
                        .addOnSuccessListener {
                            Log.d("Checkout", "Payment data updated successfully")
                            // Handle successful update, e.g., display a success message
                        }
                        .addOnFailureListener { e ->
                            Log.e("Checkout", "Error updating payment data", e)
                            // Handle update failure, e.g., display an error message
                        }
                } else {
                    // If the payment doesn't exist, display an error message or take appropriate action
                    Log.e("Checkout", "Payment data not found for update")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Checkout", "Error checking payment existence", e)
                // Handle the error
            }
    }
}

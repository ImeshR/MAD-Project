package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCheckoutBinding
import com.google.firebase.firestore.FirebaseFirestore

class Checkout : AppCompatActivity() {

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
            insertPaymentData(address, zip, city, tp, price)
        }

        binding.editDilivaryDetails.setOnClickListener {
            startActivity(Intent(this, UpdateDeliveryDetailsActivity::class.java))
        }
    }

    private fun insertPaymentData(address: String?, zip: String?, city: String?, tp: String?, price: String?) {
        // Validate the input data here if necessary

        // Create a new payment document in Firestore
        val payment = hashMapOf(
            "address" to address,
            "zip" to zip,
            "city" to city,
            "tp" to tp,
            "price" to price
        )

        firestore.collection("payments")
            .add(payment)
            .addOnSuccessListener { documentReference ->
                Log.d("Checkout", "Payment data inserted with ID: ${documentReference.id}")
                // Handle successful insertion, e.g., display a success message
            }
            .addOnFailureListener { e ->
                Log.e("Checkout", "Error inserting payment data", e)
                // Handle insertion failure, e.g., display an error message
            }
    }
}
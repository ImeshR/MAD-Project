package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.example.madproject.databinding.ActivityUpdateDeliveryDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class UpdateDeliveryDetailsActivity : AppCompatActivity() {

    private lateinit var address: EditText
    private lateinit var zip: EditText
    private lateinit var city: EditText
    private lateinit var track: EditText
    private lateinit var updatebtn: Button

    private val database = FirebaseDatabase.getInstance()
    private val deliveryDetailsRef = database.getReference("delivery_details")
    private lateinit var binding: ActivityUpdateDeliveryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delivery_details)

        binding = ActivityUpdateDeliveryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the EditText fields and the checkout button
        address = findViewById(R.id.Address)
        zip = findViewById(R.id.Zip)
        city = findViewById(R.id.City)
        track= findViewById(R.id.Track)
        updatebtn = findViewById(R.id.updatebtn)

        // Get the ID of the delivery details to be updated
        val deliveryDetailsId = intent.getStringExtra("deliveryDetailsId")

        // Retrieve the current delivery details from Firebase
        if (deliveryDetailsId != null) {
            deliveryDetailsRef.child(deliveryDetailsId).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot.exists()) {
                        val deliveryDetails = dataSnapshot.value as Map<*, *>
                        address.setText(deliveryDetails["address"].toString())
                        zip.setText(deliveryDetails["zip"].toString())
                        city.setText(deliveryDetails["city"].toString())
                        track.setText(deliveryDetails["track"].toString())

                    } else {
                        Toast.makeText(this, "Failed to retrieve delivery details", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to retrieve delivery details", Toast.LENGTH_SHORT).show()
                }
        }


        binding.updatebtn.setOnClickListener {
            Log.d("Add_delivery_details", "Checkout button clicked")
            Toast.makeText(this, "Delivery details stored successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Checkout::class.java))
        }



        // Set an OnClickListener on the update button
//        updatebtn.setOnClickListener {
//
//            // Get the text from the EditText fields
//            val address = address.text.toString().trim()
//            val zip = zip.text.toString().trim()
//            val city = city.text.toString().trim()
//            val track = track.text.toString().trim()
//
//
//            // Create a map of the delivery details to be updated
//            val updateMap = hashMapOf<String, Any>(
//                "address" to address,
//                "zip" to zip,
//                "city" to city,
//                "track" to track,
//
//            )
//
//            // Update the delivery details in Firebase
//            if (deliveryDetailsId != null) {
//                deliveryDetailsRef.child(deliveryDetailsId).updateChildren(updateMap)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this, " updated successfully", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(this, "Failed to update ", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            }
//        }
    }
}

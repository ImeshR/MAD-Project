package com.example.madproject.Activity.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.google.firebase.database.FirebaseDatabase

class Add_delivery_details : AppCompatActivity() {

    private lateinit var address: EditText
    private lateinit var zip: EditText
    private lateinit var city: EditText
    private lateinit var track: EditText
    private lateinit var tp: EditText
    private lateinit var checkout: Button

    private val database = FirebaseDatabase.getInstance()
    private val deliveryDetailsRef = database.getReference("delivery_details")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_delivery_details)


        // Initialize the EditText fields and the checkout button
        address = findViewById(R.id.address)
        zip = findViewById(R.id.zip)
        city = findViewById(R.id.city)
        track= findViewById(R.id.track)
        tp = findViewById(R.id.tp)
        checkout = findViewById(R.id.checkout)


        // Set an OnClickListener on the checkout button
        checkout.setOnClickListener {
            // Get the text from the EditText fields
            val address = address.text.toString().trim()
            val zip = zip.text.toString().trim()
            val city = city.text.toString().trim()
            val track = track.text.toString().trim()
            val tp = tp.text.toString().trim()

            // Create a map of the delivery details
            val deliveryDetails = hashMapOf(
                "address" to address,
                "zip" to zip,
                "city" to city,
                "track" to track,
                "tp" to tp
            )

            // Store the delivery details in Firebase
            // Store the delivery details in Firebase
            deliveryDetailsRef.push().setValue(deliveryDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Delivery details stored successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to store delivery details", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

































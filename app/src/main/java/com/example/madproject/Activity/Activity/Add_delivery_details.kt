package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.example.madproject.databinding.ActivityAddDeliveryDetailsBinding

class Add_delivery_details : AppCompatActivity() {

    private lateinit var address: EditText
    private lateinit var zip: EditText
    private lateinit var city: EditText
    private lateinit var tp: EditText
    private lateinit var checkout: Button
    private lateinit var binding: ActivityAddDeliveryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDeliveryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        address = binding.address
        zip = binding.zip
        city = binding.city
        tp = binding.tp
        checkout = binding.navcheckout




        val price = intent.getStringExtra("price")
        Log.d("TAG", "Received Value: $price")

        checkout.setOnClickListener {
            Log.d("Add_delivery_details", "Checkout button clicked")

            val inputAddress = address.text.toString()
            val inputZip = zip.text.toString()
            val inputCity = city.text.toString()
            val inputTp = tp.text.toString()

            Log.d("Add_delivery_details", "Address: $inputAddress")
            Log.d("Add_delivery_details", "Zip: $inputZip")
            Log.d("Add_delivery_details", "City: $inputCity")
            Log.d("Add_delivery_details", "TP: $inputTp")

            val intent = Intent(this, Checkout::class.java)
            intent.putExtra("address", inputAddress)
            intent.putExtra("zip", inputZip)
            intent.putExtra("city", inputCity)
            intent.putExtra("tp", inputTp)
            intent.putExtra("checkoutprice",price)
            startActivity(intent)
        }
    }
}

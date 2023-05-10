package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCheckoutBinding

class Checkout : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val intent = intent
        val addressString = intent.getStringExtra("address")
        val zipString = intent.getStringExtra("zip")
        val cityString = intent.getStringExtra("city")
        val tpString = intent.getStringExtra("tp")






        binding.editDilivaryDetails.setOnClickListener {
            startActivity(
                Intent(this, UpdateDeliveryDetailsActivity::class.java)
            )
        }
    }
}
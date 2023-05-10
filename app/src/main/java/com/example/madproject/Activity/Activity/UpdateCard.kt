package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivityUpdateCardBinding

class UpdateCard : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)

        val profileName=intent.getStringExtra("cardID")

        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToCardHandle.setOnClickListener {
            startActivity(
                Intent(this, CardHandle::class.java)
            )
        }
    }
}
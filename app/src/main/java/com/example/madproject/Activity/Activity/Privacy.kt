package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivityMainBinding
import com.example.madproject.databinding.ActivityPrivacyBinding

class Privacy : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteUserProfile.setOnClickListener {
            startActivity(
                Intent(this, DeleteUserAccount::class.java)
            )
        }

        binding.updateUserPass.setOnClickListener {
            startActivity(
                Intent(this, updateUserPassword::class.java)
            )
        }
    }


}
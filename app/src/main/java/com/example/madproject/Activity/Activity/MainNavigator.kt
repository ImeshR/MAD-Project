package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivityMainNavigatorBinding

class MainNavigator : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigator)

        binding = ActivityMainNavigatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navMainLoginBtn.setOnClickListener {
            startActivity(
                Intent(this, Login::class.java)
            )
        }

        binding.navMainSignUpBtn.setOnClickListener {
            startActivity(
                Intent(this, SignUp::class.java)
            )
        }
    }
}
package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            startActivity(
                android.content.Intent(this, Login::class.java)
            )
        }

        binding.linktoLogin.setOnClickListener {
            startActivity(
                android.content.Intent(this, Login::class.java)
            )
        }

        binding.navigateBackSignUp.setOnClickListener {
            startActivity(
                android.content.Intent(this, MainNavigator::class.java)
            )
        }

    }
}
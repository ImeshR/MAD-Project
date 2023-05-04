package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigateBackLogin.setOnClickListener {
            startActivity(
                android.content.Intent(this, MainNavigator::class.java)
            )
        }

        binding.linktoSignUp.setOnClickListener {
            startActivity(
                android.content.Intent(this, SignUp::class.java)
            )
        }

        binding.loginBtn.setOnClickListener {
            startActivity(
                android.content.Intent(this, MainActivity::class.java)
            )
        }
    }
}
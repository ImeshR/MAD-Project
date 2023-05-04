package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madproject.R
import com.example.madproject.databinding.ActivityLoginBinding
import com.example.madproject.databinding.ActivityMainNavigatorBinding
import com.google.firebase.auth.FirebaseAuth

class MainNavigator : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigatorBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigator)

        firebaseAuth = FirebaseAuth.getInstance()
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

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
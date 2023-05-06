package com.example.madproject.Activity.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.fragment.app.Fragment
import com.example.madproject.R
import com.example.madproject.databinding.ActivityMainBinding

class payment_profile : AppCompatActivity() {

    lateinit var  navpay : Constraint

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_profile)
    }
}
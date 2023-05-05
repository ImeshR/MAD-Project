package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.madproject.R
import com.example.madproject.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signUpBtn.setOnClickListener {
            val firstName = binding.singUpFirstName.text.toString()
            val lastName = binding.singUpLastName.text.toString()
            val email = binding.singUpEmail.text.toString()
            val mobileNumber = binding.singUpNumber.text.toString()
            val password = binding.singUpPass.text.toString()
            val confirmPassword = binding.singUpConfirmPass.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPhoneNumber(mobileNumber)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && mobileNumber.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == (confirmPassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        saveUser(it.result?.user?.uid.toString(), firstName, lastName, email, mobileNumber, password)
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed !!!", Toast.LENGTH_SHORT).show()
            }
        }


        //Intent Paths

        binding.linktoLogin.setOnClickListener {
            startActivity(
                Intent(this, Login::class.java)
            )
        }

        binding.navigateBackSignUp.setOnClickListener {
            startActivity(
                Intent(this, MainNavigator::class.java)
            )
        }
    }

    private fun saveUser(uid: String, firstName: String, lastName: String, email: String, mobileNumber: String, password: String) {
        val user = UserData(uid, firstName, lastName, email, mobileNumber, password)
        firestore.collection("users").document(uid).set(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                intent.putExtra("USER_ID", uid)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(number: String): Boolean {
        return number.length == 10 && TextUtils.isDigitsOnly(number)
    }
}
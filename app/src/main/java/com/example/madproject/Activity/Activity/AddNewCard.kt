package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCardAddFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddNewCard : AppCompatActivity() {

    lateinit var binding: ActivityCardAddFormBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_add_form)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding = ActivityCardAddFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNewCardBtn.setOnClickListener {
            val cardHolderName = binding.CardHolderName.text.toString()
            val cardNumber = binding.CardNumber.text.toString()
            val expMonth = binding.expMonth.text.toString()
            val expYear = binding.expYear.text.toString()
            val cvv = binding.cvv.text.toString()

            if (validateInput(cardHolderName, cardNumber, expMonth, expYear, cvv)) {
                val cardDetailsData = CardDetailsData(
                    userId = firebaseAuth.currentUser?.uid,
                    cardHolderName = cardHolderName,
                    CardNumber = cardNumber.toLong(),
                    expMonth = expMonth.toInt(),
                    expYear = expYear.toInt(),
                    cvv = cvv.toInt()
                )

                addCardToFirestore(cardDetailsData)
            }

            startActivity(
                Intent(this, CardHandle::class.java)
            )
        }

        binding.backToCardHandle.setOnClickListener {
            startActivity(
                Intent(this, CardHandle::class.java)
            )
        }
    }

    private fun validateInput(
        cardHolderName: String,
        cardNumber: String,
        expMonth: String,
        expYear: String,
        cvv: String
    ): Boolean {
        if (TextUtils.isEmpty(cardHolderName) || TextUtils.isEmpty(cardNumber) ||
            TextUtils.isEmpty(expMonth) || TextUtils.isEmpty(expYear) || TextUtils.isEmpty(cvv)
        ) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }

        if (cardNumber.length != 16) {
            Toast.makeText(this, "Card number must contain 16 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        val expMonthInt = expMonth.toIntOrNull()
        if (expMonthInt == null || expMonthInt !in 1..12) {
            Toast.makeText(this, "Exp month must be between 1 to 12", Toast.LENGTH_SHORT).show()
            return false
        }

        if (expYear.length != 4) {
            Toast.makeText(this, "Exp date must be a year with 4 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        if (cvv.length != 3) {
            Toast.makeText(this, "CVV number must contain 3 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun addCardToFirestore(cardDetailsData: CardDetailsData) {
        val collectionRef = firestore.collection("cards")
        collectionRef.add(cardDetailsData)
            .addOnSuccessListener {
                Toast.makeText(this, "Card added successfully", Toast.LENGTH_SHORT).show()
                // Handle success, such as navigating to another activity
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add card: ${e.message}", Toast.LENGTH_SHORT).show()
                // Handle failure, such as displaying an error message
            }
    }
}

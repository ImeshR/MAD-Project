package com.example.madproject.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.databinding.ActivityCardHandleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CardHandle : AppCompatActivity() {

    private lateinit var cardRecyclerView: RecyclerView
    private val cardList: MutableList<CardDetailsData> = mutableListOf() // Initialize an empty cardList
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityCardHandleBinding
    private lateinit var userid : String
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_handle)

        retrieveCardDetails()

//        binding = ActivityCardHandleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        // Initialize RecyclerView
//        cardRecyclerView = findViewById(R.id.addedCardFlow)
//        cardRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Create and set the adapter
//        cardAdapter = CardAdapter(cardList)
//        cardRecyclerView.adapter = cardAdapter
//
//        // Retrieve card data from your data source
//        //retrieveCardData()
//
//        binding.addNewWallet.setOnClickListener {
//            startActivity(
//                Intent(this, AddNewCard::class.java)
//            )
//        }
    }

//    private fun retrieveCardData() {
//        val userId = firebaseAuth.currentUser?.uid // Replace with your authentication logic
//
//        val db = FirebaseFirestore.getInstance()
//        val cardsCollection = db.collection("cards") // Replace with your Firestore collection name
//
//        // Perform the Firestore query to fetch the cards associated with the user
//        cardsCollection
//            .whereEqualTo("userId", userId)
//            .get()
//            .addOnSuccessListener { documents ->
//                // Clear the cardList before adding new data
//                cardList.clear()
//
//                // Iterate through the retrieved documents and extract the card data
//                for (document in documents) {
//                    val card = document.toObject(CardDetailsData::class.java)
//                    cardList.add(card)
//                }
//
//                // Notify the adapter that the data has changed
//                cardAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                // Handle any errors that occurred during data retrieval
//                Toast.makeText(this, "Failed to retrieve card data: ${exception.message}", Toast.LENGTH_SHORT).show()
//            }
//    }

    fun retrieveCardDetails(){
        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid ?: ""

        println("User second ID: $userid")

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()


        val cards =  arrayListOf<CardData>()

        val collectionRef = firebaseFirestore.collection("cards").whereEqualTo("userId", userid)

        val recyclerView = findViewById<RecyclerView>(R.id.addedCardFlow)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        val cards = arrayListOf<CardData>()
        val adapter = CardAdapterN(cards)

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val cardNum = document.get("cardNumber") as Number?
                val documentID = document.id
                println("$cardNum")

                val cdata = CardData("$cardNum")
                cards.add(cdata)


                adapter.deleteCardBuutonClick = {
                    deleteCard(cardNum)
                }

                adapter.editCardBuutonClick = {
                    val intent = Intent(this, UpdateCard::class.java)
                    intent.putExtra("cardID","$documentID")
                    startActivity(intent)
                }

            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }

    private fun deleteCard(cardNum: Number?) {


    }

}
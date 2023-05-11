//package com.example.madproject.Activity.Activity
//
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.madproject.R
//
//class CardAdapter(private val cardList: List<CardDetailsData>) :
//    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
//
//    // ViewHolder class that holds the views for a single card item
//    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        // Define the views in the card item layout
//        val cardNumberTextView: TextView = itemView.findViewById(R.id.enteredCardNumber)
////        val cardHolderNameTextView: TextView = itemView.findViewById(R.id.cardHolderNameTextView)
//
//        val updatecardBtn: ImageView = itemView.findViewById(R.id.editCardBtn)
//        val deletecardBtn: ImageView = itemView.findViewById(R.id.editDeleteBtn)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
//        // Inflate the item_card.xml layout file for the card item
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
//        return CardViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
//        // Retrieve the card details at the given position
//        val cardDetails = cardList[position]
//
//        // Bind the card details to the views in the ViewHolder
//        holder.cardNumberTextView.text = cardDetails.CardNumber.toString()
////        holder.cardHolderNameTextView.text = cardDetails.cardHolderName
//    }
//
//    override fun getItemCount(): Int {
//        // Return the total number of cards in the list
//        return cardList.size
//    }
//}

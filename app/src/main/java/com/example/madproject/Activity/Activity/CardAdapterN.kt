package com.example.madproject.Activity.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madproject.R

class CardAdapterN(private val cards: MutableList<CardData>): RecyclerView.Adapter<CardViewHolder>(){
    var deleteCardBuutonClick: (() -> Unit)? = null
    var editCardBuutonClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        holder.cardNum.text = card.cardNum

        holder.Deletebtn.setOnClickListener {
            deleteCardBuutonClick?.invoke()
        }

        holder.Editbtn.setOnClickListener {
            editCardBuutonClick?.invoke()
        }

    }

    override fun getItemCount() = cards.size
}
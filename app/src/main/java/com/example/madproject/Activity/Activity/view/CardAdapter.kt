package com.example.madproject.Activity.Activity.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.Activity.Activity.CardDetailsData
import com.example.madproject.R
import android.text.Editable

class CardAdapter(val c:Context,val cardList:ArrayList<CardDetailsData>): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(val v:View): RecyclerView.ViewHolder(v){
        val cardNum = v.findViewById<EditText>(R.id.s_cardNumberN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.s_carddetailsnew,parent, false)
        return CardViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val newList = cardList[position]
    }
}
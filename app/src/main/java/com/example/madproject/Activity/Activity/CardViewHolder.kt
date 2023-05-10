package com.example.madproject.Activity.Activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R

class CardViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val cardNum : TextView = itemView.findViewById(R.id.CardNumnew)
        val Editbtn : ImageView = itemView.findViewById(R.id.editCardBtn)
        val Deletebtn : ImageView = itemView.findViewById(R.id.editDeleteBtn)

}